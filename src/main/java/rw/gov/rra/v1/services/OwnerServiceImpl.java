package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.dtos.request.CreateOwnerDTO;
import rw.gov.rra.v1.exceptions.ResourceNotFoundException;
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

    @Override
    public Page<Owner> getAllOwners(Pageable pageable) {
        return ownerRepo.findAll(pageable);

    }

    @Override
    public Owner getOwnerByID(UUID id) {
        return ownerRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("owner", "id", id.toString()));
    }

    @Override
    public Page<Owner> search(Pageable pageable, String searchKey) {
        return ownerRepo.search(pageable, searchKey);
    }
}
