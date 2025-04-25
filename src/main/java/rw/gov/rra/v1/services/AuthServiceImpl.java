package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.interfaces.IAuthService;
import rw.gov.rra.v1.repositories.IUserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserRepository userRepo;
}
