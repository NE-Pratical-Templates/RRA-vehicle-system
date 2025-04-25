package rw.gov.rra.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.interfaces.IVehicleService;
import rw.gov.rra.v1.repositories.IVehicleRepository;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {
    private  final IVehicleRepository vehicleRepo;
}
