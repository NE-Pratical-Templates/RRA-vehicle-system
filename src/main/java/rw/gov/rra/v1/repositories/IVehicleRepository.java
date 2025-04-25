package rw.gov.rra.v1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.gov.rra.v1.models.Owner;
import rw.gov.rra.v1.models.Vehicle;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, UUID> {
    Optional<Vehicle> findByChassisNumber(String chassisNumber);

    Page<Vehicle> findByOwner(Owner owner, Pageable pageable);

    @Query("SELECT u FROM Vehicle u" +
            " WHERE (lower(u.chassisNumber)  LIKE ('%' || lower(:searchKey) || '%')) " +
            " OR (lower(u.owner.nationalId) LIKE ('%' || lower(:searchKey) || '%')) " +
            " OR (lower(u.plate.plateNumber) LIKE ('%' || lower(:searchKey) || '%'))")
    Page<Vehicle> search(Pageable pageable, String searchKey);
}
