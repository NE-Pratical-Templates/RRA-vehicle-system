package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rw.gov.rra.v1.dtos.request.CreateTransferDTO;
import rw.gov.rra.v1.enums.EPlateStatus;
import rw.gov.rra.v1.exceptions.ResourceAlreadyExistsException;
import rw.gov.rra.v1.interfaces.ITransferService;
import rw.gov.rra.v1.models.Owner;
import rw.gov.rra.v1.models.Plate;
import rw.gov.rra.v1.models.Transfer;
import rw.gov.rra.v1.models.Vehicle;
import rw.gov.rra.v1.repositories.IOwnerRepository;
import rw.gov.rra.v1.repositories.IPlateRepository;
import rw.gov.rra.v1.repositories.ITransferRepository;
import rw.gov.rra.v1.repositories.IVehicleRepository;
import rw.gov.rra.v1.standalone.MailService;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements ITransferService {
    private final ITransferRepository transferRepo;
    private final IVehicleRepository vehicleRepo;
    private final IOwnerRepository ownerRepo;
    private final IPlateRepository plateRepo;
    private final MailService mailService;

    @Override
    public Transfer createTransfer(CreateTransferDTO dto) {
        Owner oldOwner = ownerRepo.findByEmailAndNationalId(
                dto.getOldOwnerEmail(),
                dto.getOldOwnerNationalID()).orElseThrow(
                () -> new ResourceAlreadyExistsException(" no owner found with that email and national id"));

        Owner newOwner = ownerRepo.findByEmailAndNationalId(
                dto.getNewOwnerEmail(),
                dto.getNewOwnerNationalID()).orElseThrow(
                () -> new ResourceAlreadyExistsException(" no owner found with that email and national id"));

        Vehicle vehicle = vehicleRepo.findByChassisNumber(dto.getVehicleChassisNumber()).orElseThrow(
                () -> new ResourceAlreadyExistsException(" no vehicle found with that chassis number"));

        Plate plate = plateRepo.findByPlateNumber(dto.getNewPlate()).orElseThrow(
                () -> new ResourceAlreadyExistsException(" no plate found with that plate number"));
        if (plate.getOwner() != newOwner)
            throw new ResourceAlreadyExistsException(" this plate does not belong to this new  owner");
        if (plate.getStatus() == EPlateStatus.INUSE || plate.getVehicle() != null)
            throw new ResourceAlreadyExistsException(" this plate is already in use");
        if (!vehicle.getOwner().getId().equals(oldOwner.getId()))
            throw new ResourceAlreadyExistsException(" this vehicle does not belong to the old owner");

        if (vehicle.getOwner().getId().equals(newOwner.getId()))
            throw new ResourceAlreadyExistsException(" this vehicle already belongs to the new owner");
        Plate oldPlate = vehicle.getPlate();
        oldPlate.setStatus(EPlateStatus.AVAILABLE);
        oldPlate.setVehicle(null);
        plateRepo.save(oldPlate);

        plate.setStatus(EPlateStatus.INUSE);
        plateRepo.save(plate);

        vehicle.setPlate(plate);
        vehicle.setOwner(newOwner);
        vehicleRepo.save(vehicle);

        Transfer transfer = new Transfer();
        transfer.setOldOwner(oldOwner);
        transfer.setNewOwner(newOwner);
        transfer.setVehicle(vehicle);
        transfer.setAmount(dto.getAmount());

        // Send Emails
        mailService.sendTransferNotification(oldOwner.getEmail(), newOwner.getEmail(), vehicle);
        return transferRepo.save(transfer);
    }

    @Override
    @Transactional()
    public Page<Transfer> getVehicleHistory(String chassisNumber, String plateNumber, Pageable pageable) {
        Vehicle vehicle = null;

        if (chassisNumber != null && !chassisNumber.isBlank() && plateNumber != null && !plateNumber.isBlank()) {
            // If both are provided, verify both match the same vehicle
            Vehicle vehicleFetched = vehicleRepo.findByChassisNumber(chassisNumber)
                    .orElseThrow(
                            () -> new ResourceAlreadyExistsException("VehicleFetched with chassis number not found"));

            if (vehicleFetched.getPlate() == null || !vehicleFetched.getPlate().getPlateNumber().equals(plateNumber)) {
                throw new ResourceAlreadyExistsException(
                        "Plate number and chassis number do not match the same vehicle");
            }

            vehicle = vehicleFetched;

        } else if (chassisNumber != null && !chassisNumber.isBlank()) {
            vehicle = vehicleRepo.findByChassisNumber(chassisNumber)
                    .orElseThrow(() -> new ResourceAlreadyExistsException("Vehicle with chassis number not found"));

        } else if (plateNumber != null && !plateNumber.isBlank()) {
            Plate plate = plateRepo.findByPlateNumber(plateNumber)
                    .orElseThrow(() -> new ResourceAlreadyExistsException("Plate number not found"));
            vehicle = plate.getVehicle();
            if (vehicle == null) {
                throw new ResourceAlreadyExistsException("No vehicle assigned to this plate");
            }

        } else {
            throw new IllegalArgumentException("You must provide either a chassis number or a plate number");
        }

        return transferRepo.findByVehicle(vehicle, pageable);
    }

}
