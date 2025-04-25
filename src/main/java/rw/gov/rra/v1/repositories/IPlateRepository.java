package rw.gov.rra.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.gov.rra.v1.models.Plate;

import java.util.UUID;

@Repository
public interface IPlateRepository extends JpaRepository<Plate, UUID> {
}
