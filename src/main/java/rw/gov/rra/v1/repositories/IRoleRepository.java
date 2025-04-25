package rw.gov.rra.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.gov.rra.v1.models.Role;

import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID> {
}
