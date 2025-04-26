package rw.gov.rra.v1.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.dtos.request.CreateVehicleDTO;
import rw.gov.rra.v1.enums.EPlateStatus;
import rw.gov.rra.v1.exceptions.BadRequestException;
import rw.gov.rra.v1.interfaces.IVehicleService;
import rw.gov.rra.v1.models.Owner;
import rw.gov.rra.v1.models.Plate;
import rw.gov.rra.v1.models.Vehicle;
import rw.gov.rra.v1.repositories.IOwnerRepository;
import rw.gov.rra.v1.repositories.IPlateRepository;
import rw.gov.rra.v1.repositories.IVehicleRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements IVehicleService {
    private final IVehicleRepository vehicleRepo;
    private final IOwnerRepository ownerRepo;
    private final IPlateRepository plateRepo;


    @Override
    @Transactional
    public Vehicle createVehicle(CreateVehicleDTO dto, UUID ownerID) {
        try {
            if (vehicleRepo.findByChassisNumber(dto.getChassisNumber()).isPresent()) {
                throw new IllegalArgumentException("Vehicle with this chassis number already exists.");
            }
            Owner owner = ownerRepo.findById(ownerID)
                    .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

            Plate plate = plateRepo.findByPlateNumber(dto.getPlateNumber())
                    .orElseThrow(() -> new EntityNotFoundException("Plate not found"));
            if (!plate.getOwner().getId().equals(owner.getId())) {
                throw new IllegalArgumentException("Plate does not belong to the selected owner");
            }
//            log.info("model name : ",plate.getVehicle().getModelName());
            if (plate.getStatus() == EPlateStatus.INUSE || plate.getVehicle() != null) {
                throw new IllegalStateException("Plate is already assigned to another vehicle");
            }
            Vehicle vehicle = new Vehicle();
            vehicle.setChassisNumber(dto.getChassisNumber());
            vehicle.setManufactureCompany(dto.getManufactureCompany());
            vehicle.setManufacturedYear(dto.getManufacturedYear());
            vehicle.setPrice(dto.getPrice());
            vehicle.setModelName(dto.getModelName());
            vehicle.setOwner(owner);
            vehicle.setPlate(plate);
            vehicleRepo.save(vehicle);
            plate.setStatus(EPlateStatus.INUSE);
            plate.setVehicle(vehicle);
            plateRepo.save(plate);
            owner.getVehicles().add(vehicle);
            ownerRepo.save(owner);

            return vehicle;
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        return vehicleRepo.findAll(pageable);
    }

    @Override
    public Page<Vehicle> getAllVehiclesByOwner(Pageable pageable, UUID ownerID) {
        Owner owner = ownerRepo.findById(ownerID).orElseThrow(() -> new UsernameNotFoundException(" no owner found wit that id "));

        return vehicleRepo.findByOwner(owner, pageable);
    }

    @Override
    public Page<Vehicle> search(Pageable pageable, String q) {
        return vehicleRepo.search(pageable, q);
    }
}
