package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.interfaces.IPlateService;
import rw.gov.rra.v1.repositories.IPlateRepository;

@Service
@RequiredArgsConstructor
public class PlateServiceImpl implements IPlateService {
private  final IPlateRepository plateRepo;
}
