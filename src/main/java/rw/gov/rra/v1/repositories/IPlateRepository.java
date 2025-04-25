package rw.gov.rra.v1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.gov.rra.v1.models.Owner;
import rw.gov.rra.v1.models.Plate;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPlateRepository extends JpaRepository<Plate, UUID> {
    Optional<Plate> findByPlateNumber(String plateRepo);

    Page<Plate> findByOwner(Owner owner, Pageable pageable);
}
