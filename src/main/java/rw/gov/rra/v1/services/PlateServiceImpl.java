package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.dtos.request.CreatePlateDTO;
import rw.gov.rra.v1.enums.EPlateStatus;
import rw.gov.rra.v1.exceptions.ResourceAlreadyExistsException;
import rw.gov.rra.v1.interfaces.IPlateService;
import rw.gov.rra.v1.models.Owner;
import rw.gov.rra.v1.models.Plate;
import rw.gov.rra.v1.repositories.IOwnerRepository;
import rw.gov.rra.v1.repositories.IPlateRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlateServiceImpl implements IPlateService {
    private final IPlateRepository plateRepo;
    private final IOwnerRepository ownerRepo;

    @Override
    public Plate createPlate(CreatePlateDTO dto, UUID ownerID) throws BadRequestException {
        Owner owner = ownerRepo.findById(ownerID).orElseThrow(() -> new UsernameNotFoundException(" no owner found wit that id "));
        Optional<Plate> uPlate = plateRepo.findByPlateNumber(dto.getPlateNumber());
        if (uPlate.isPresent())
            throw new ResourceAlreadyExistsException(" plate with that plate number already exists");
        Plate plate = new Plate();
        plate.setStatus(EPlateStatus.AVAILABLE);
        plate.setOwner(owner);
        plate.setPlateNumber(dto.getPlateNumber());
        plate.setVehicle(null);
        owner.getPlates().add(plate);
        ownerRepo.save(owner);

        return plateRepo.save(plate);
    }

    @Override
    public Page<Plate> getAllPlatesOfOwnerByAdmin(Pageable pageable, UUID ownerID) {
        Owner owner = ownerRepo.findById(ownerID).orElseThrow(() -> new UsernameNotFoundException(" no owner found wit that id "));
        return plateRepo.findByOwner(owner, pageable);
    }
}
