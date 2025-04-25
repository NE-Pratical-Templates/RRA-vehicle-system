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
import rw.gov.rra.v1.dtos.request.CreatePlateDTO;
import rw.gov.rra.v1.dtos.response.ApiResponseDTO;
import rw.gov.rra.v1.interfaces.IPlateService;
import rw.gov.rra.v1.utils.Constants;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plates")
@RequiredArgsConstructor
public class PlateController {
    private final IPlateService plateService;

    @Operation(summary = "Register a new plate"
            , security = @SecurityRequirement(name = "bearerAuth")

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plage registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/{OwnerID}")
    ResponseEntity<ApiResponseDTO> registerPlate(@Valid @RequestBody CreatePlateDTO dto, @PathVariable(required = true) UUID OwnerID
    ) throws BadRequestException {
        return ResponseEntity.ok(ApiResponseDTO.success("plate created successfully", plateService.createPlate(dto, OwnerID)));
    }

    //     get all  plates of owner by admin
    @Operation(
            summary = "get all  plates of owner by admin",
            description = "get all  plates of owner by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  plates of owner  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/{OwnerID}")
    private ResponseEntity<ApiResponseDTO> getAllPlatesOfOwner(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit, @PathVariable(required = true) UUID OwnerID) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("plates fetched successfully", plateService.getAllPlatesOfOwnerByAdmin(pageable,OwnerID )));
    }
}
