package rw.gov.rra.v1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.gov.rra.v1.dtos.request.CreateVehicleDTO;
import rw.gov.rra.v1.dtos.response.ApiResponseDTO;
import rw.gov.rra.v1.interfaces.IVehicleService;
import rw.gov.rra.v1.utils.Constants;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private  final IVehicleService vehicleService;
    @Operation(summary = "Register a new vehicle"
            , security = @SecurityRequirement(name = "bearerAuth")

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/{OwnerID}")
    ResponseEntity<ApiResponseDTO> registerVehicle(@Valid @RequestBody CreateVehicleDTO dto,@PathVariable(required = true) UUID OwnerID) throws BadRequestException {
        return ResponseEntity.ok(ApiResponseDTO.success("vehicle created successfully", vehicleService.createVehicle(dto ,OwnerID)));
    }

    //     get all  vehicles by admin
    @Operation(
            summary = "get all  vehicles by admin",
            description = "get all  plates by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  plates by admin  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    private ResponseEntity<ApiResponseDTO> getAllVehiclesByAdmin(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("vehicles fetched successfully", vehicleService.getAllVehicles(pageable)));
    }

    //     get all  vehicles of owner by admin
    @Operation(
            summary = "get all  vehicles of owner by admin",
            description = "get all  vehicles of owner by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  vehicles of owner  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/owner/{OwnerID}")
    private ResponseEntity<ApiResponseDTO> getAllVehiclesByAdmin(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit, @PathVariable(required = true) UUID OwnerID) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("vehicles fetched successfully", vehicleService.getAllVehiclesByOwner(pageable,OwnerID )));
    }

    //    search   vehicle by admin
    @Operation(
            summary = " search   vehicle by admin ",
            description = " search   vehicle by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/search")
    public ResponseEntity<ApiResponseDTO> search(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "q") String q
    ) {
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponseDTO.success("Users fetched successfully", this.vehicleService.search(pageable, q)));
    }
}
