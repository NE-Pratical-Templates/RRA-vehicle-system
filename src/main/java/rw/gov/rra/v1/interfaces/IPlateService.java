package rw.gov.rra.v1.interfaces;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.gov.rra.v1.dtos.request.CreatePlateDTO;
import rw.gov.rra.v1.models.Plate;

import java.util.UUID;

public interface IPlateService {
    Plate createPlate(@Valid CreatePlateDTO dto,UUID OwnerID) throws BadRequestException;

    Page<Plate> getAllPlatesOfOwnerByAdmin(Pageable pageable, UUID ownerID);
}
