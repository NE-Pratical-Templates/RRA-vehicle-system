package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.interfaces.IUserService;
import rw.gov.rra.v1.repositories.IUserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepo;
}
