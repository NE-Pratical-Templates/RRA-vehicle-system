package rw.gov.rra.v1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.gov.rra.v1.models.Transfer;
import rw.gov.rra.v1.models.Vehicle;

import java.util.UUID;

@Repository
public interface ITransferRepository  extends JpaRepository<Transfer, UUID> {

    Page<Transfer> findByVehicle(Vehicle vehicle, Pageable pageable);
}
