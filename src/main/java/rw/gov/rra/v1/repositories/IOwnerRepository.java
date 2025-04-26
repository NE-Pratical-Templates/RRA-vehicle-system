package rw.gov.rra.v1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.gov.rra.v1.models.Owner;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner, UUID> {

    @Query("SELECT u FROM Owner u" +
            " WHERE (lower(u.firstName)  LIKE ('%' || lower(:searchKey) || '%')) " +
            " OR (lower(u.lastName) LIKE ('%' || lower(:searchKey) || '%')) " +
            " OR (lower(u.nationalId) LIKE ('%' || lower(:searchKey) || '%')) " +
            " OR (lower(u.mobile) LIKE ('%' || lower(:searchKey) || '%')) " +
            " OR (lower(u.email) LIKE ('%' || lower(:searchKey) || '%'))")
    Page<Owner> search(Pageable pageable, String searchKey);

    @Query("SELECT o FROM Owner o " +
            "LEFT JOIN FETCH o.plates " +
            "LEFT JOIN FETCH o.vehicles " +
            "WHERE o.id = :id")
    Optional<Owner> findByIdWithPlatesAndVehicles(@Param("id") UUID id);

}
