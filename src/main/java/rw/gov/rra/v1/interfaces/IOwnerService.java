package rw.gov.rra.v1.interfaces;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.gov.rra.v1.dtos.request.CreateOwnerDTO;
import rw.gov.rra.v1.models.Owner;

import java.util.UUID;

public interface IOwnerService {
    Owner createOwner(@Valid CreateOwnerDTO dto) throws BadRequestException;

    Page<Owner> getAllOwners(Pageable pageable);

    Owner getOwnerByID(UUID id);

    Page<Owner> search(Pageable pageable, String q);
}
