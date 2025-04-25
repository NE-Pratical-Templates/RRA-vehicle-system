package rw.gov.rra.v1.interfaces;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.gov.rra.v1.dtos.request.CreateVehicleDTO;
import rw.gov.rra.v1.models.Vehicle;

import java.util.UUID;

public interface IVehicleService {
    Vehicle createVehicle(@Valid CreateVehicleDTO dto, UUID ownerID);

    Page<Vehicle> getAllVehicles(Pageable pageable);

    Page<Vehicle> getAllVehiclesByOwner(Pageable pageable, UUID ownerID);

    Page<Vehicle> search(Pageable pageable, String q);
}
