package rw.gov.rra.v1.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.gov.rra.v1.interfaces.IPlateService;

@RestController
@RequestMapping("/api/v1/plates")
@RequiredArgsConstructor
public class PlateController {
    private  final IPlateService plateService;
}
