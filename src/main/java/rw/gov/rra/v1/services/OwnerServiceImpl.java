package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.interfaces.IOwnerService;
import rw.gov.rra.v1.repositories.IOwnerRepository;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl  implements IOwnerService {
    private  final IOwnerRepository ownerRepo;
}
