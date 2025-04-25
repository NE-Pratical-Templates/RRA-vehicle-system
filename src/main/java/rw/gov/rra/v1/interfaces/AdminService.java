package rw.gov.rra.v1.interfaces;

import jakarta.validation.Valid;
import rw.gov.rra.v1.dtos.request.CreateUserDTO;
import rw.gov.rra.v1.models.User;

public interface AdminService {
    User registerNewAdmin(@Valid CreateUserDTO dto);
}
