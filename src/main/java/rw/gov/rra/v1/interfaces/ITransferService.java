package rw.gov.rra.v1.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import rw.gov.rra.v1.dtos.request.CreateTransferDTO;
import rw.gov.rra.v1.models.Transfer;

public interface ITransferService {
    Transfer createTransfer(@Valid CreateTransferDTO dto);

    Page<Transfer> getVehicleHistory(String chassisNumber, String plateNumber, Pageable pageable);
}
