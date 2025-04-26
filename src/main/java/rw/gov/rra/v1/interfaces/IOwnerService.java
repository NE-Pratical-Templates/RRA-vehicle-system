package rw.gov.rra.v1.interfaces;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.gov.rra.v1.dtos.request.CreateOwnerDTO;
import rw.gov.rra.v1.dtos.response.OwnerResponseDTO;
import rw.gov.rra.v1.models.Owner;

import java.util.UUID;

public interface IOwnerService {
    Owner createOwner(@Valid CreateOwnerDTO dto) throws BadRequestException;

    Page<OwnerResponseDTO> getAllOwners(Pageable pageable);

//    OwnerFullResponseDTO getOwnerByID(UUID id);
    Owner getOwnerByID(UUID id);

    Page<Owner> search(Pageable pageable, String q);
}
