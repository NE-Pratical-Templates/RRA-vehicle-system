package rw.gov.rra.v1.startup;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rw.gov.rra.v1.enums.EAccountStatus;
import rw.gov.rra.v1.enums.ERole;
import rw.gov.rra.v1.exceptions.BadRequestException;
import rw.gov.rra.v1.models.Role;
import rw.gov.rra.v1.models.User;
import rw.gov.rra.v1.repositories.IRoleRepository;
import rw.gov.rra.v1.repositories.IUserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final IRoleRepository roleRepo;
    private final IUserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(IRoleRepository roleRepo, IUserRepository userRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // Roles to be created
        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.ADMIN);
        roles.add(ERole.STANDARD);

        for (ERole role : roles) {
            // Check if the role already exists in the database
            if (!roleRepo.existsByName(role)) {
                // If not, create and save the new role
                Role newRole = new Role(role, role.toString());
                roleRepo.save(newRole);
                log.info("Created: {}", role);
            } else {
                log.info("{} already exists.", role);
            }
        }
        Optional<User> admin = userRepo.findByEmail("admin@gmail.com");
        if (admin.isEmpty()) {
            User newAdmin = new User();
            newAdmin.setFirstName("admin");
            newAdmin.setLastName("admin");
            newAdmin.setEmail("admin@gmail.com");
            newAdmin.setPassword(encoder.encode("admin"));
            newAdmin.setMobile("0799999999");
            newAdmin.setStatus(EAccountStatus.ACTIVE);
            newAdmin.setNationalId("1199980012345678");
            Role role = roleRepo.findByName(ERole.ADMIN).orElseThrow(
                    () -> new BadRequestException("ADMIN Role not set"));
            newAdmin.setRoles(Collections.singleton(role));

            userRepo.save(newAdmin);
        } else {
            log.info("{} already exists.", admin.get().getEmail());

        }
    }
}
