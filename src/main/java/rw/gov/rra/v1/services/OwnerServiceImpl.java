package rw.gov.rra.v1.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.dtos.request.CreateOwnerDTO;
import rw.gov.rra.v1.dtos.response.OwnerResponseDTO;
import rw.gov.rra.v1.interfaces.IOwnerService;
import rw.gov.rra.v1.models.Owner;
import rw.gov.rra.v1.repositories.IOwnerRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements IOwnerService {
    private final IOwnerRepository ownerRepo;

    @Override
    public Owner createOwner(CreateOwnerDTO dto) throws BadRequestException {
        try {
            Owner owner = new Owner();
            owner.setFirstName(dto.getFirstName());
            owner.setLastName(dto.getLastName());
            owner.setEmail(dto.getEmail());
            owner.setMobile(dto.getMobile());
            owner.setNationalId(dto.getNationalId());
            owner.setDob(dto.getDob());
            owner.setAddress(dto.getAddress());
            return ownerRepo.save(owner);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException(ex);
        }
    }

    public Page<OwnerResponseDTO> getAllOwners(Pageable pageable) {
        return ownerRepo.findAll(pageable)
                .map(this::convertToDto);
    }



    @Override
    public Owner getOwnerByID(UUID id) {
    return  ownerRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
    }

    private OwnerResponseDTO convertToDto(Owner owner) {
        return new OwnerResponseDTO(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail(),
                owner.getAddress(),
                owner.getNationalId(),
                owner.getMobile(),
                owner.getDob()
        );
    }
//    @Override
//    public OwnerFullResponseDTO getOwnerByID(UUID id) {
//        Owner owner = ownerRepo.findByIdWithPlatesAndVehicles(id)
//                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
//
//        Set<String> plateNumbers = owner.getPlates().stream()
//                .map(Plate::getPlateNumber)
//                .collect(Collectors.toSet());
//
//        Set<String> vehicleChassis = owner.getVehicles().stream()
//                .map(Vehicle::getChassisNumber)
//                .collect(Collectors.toSet());
//
//        return new OwnerFullResponseDTO(
//                owner.getId(),
//                owner.getFirstName(),
//                owner.getLastName(),
//                owner.getEmail(),
//                owner.getAddress(),
//                owner.getNationalId(),
//                owner.getMobile(),
//                owner.getDob(),
//                plateNumbers,
//                vehicleChassis
//        );
//    }


    @Override
    public Page<Owner> search(Pageable pageable, String searchKey) {
        return ownerRepo.search(pageable, searchKey);
    }
}
