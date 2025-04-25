package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.dtos.request.CreateUserDTO;
import rw.gov.rra.v1.enums.EAccountStatus;
import rw.gov.rra.v1.enums.ERole;
import rw.gov.rra.v1.exceptions.BadRequestException;
import rw.gov.rra.v1.interfaces.AdminService;
import rw.gov.rra.v1.models.Role;
import rw.gov.rra.v1.models.User;
import rw.gov.rra.v1.repositories.IRoleRepository;
import rw.gov.rra.v1.repositories.IUserRepository;
import rw.gov.rra.v1.utils.Utility;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final IUserRepository userRepo;
    private final IRoleRepository roleRepo;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public User registerNewAdmin(CreateUserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setNationalId(dto.getNationalId());
        user.setDob(dto.getDob());

        try {
            Role role = roleRepo.findByName(ERole.STANDARD).orElseThrow(
                    () -> new BadRequestException("STANDARD Role not set"));
            String encodedPassword = passwordEncoder.encode(dto.getPassword());

            user.setPassword(encodedPassword);
            user.setRoles(Collections.singleton(role));
            user.setStatus(EAccountStatus.ACTIVE);
            return userRepo.save(user);
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = Utility.getConstraintViolationMessage(ex, user);
            throw new BadRequestException(errorMessage, ex);
        }
    }
}
