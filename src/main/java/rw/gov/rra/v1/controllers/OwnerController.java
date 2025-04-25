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
import rw.gov.rra.v1.dtos.request.CreateOwnerDTO;
import rw.gov.rra.v1.dtos.response.ApiResponseDTO;
import rw.gov.rra.v1.interfaces.IOwnerService;
import rw.gov.rra.v1.utils.Constants;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {
    private final IOwnerService ownerService;

    @Operation(summary = "Register a new owner"
            , security = @SecurityRequirement(name = "bearerAuth")

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    ResponseEntity<ApiResponseDTO> registerOwner(@Valid @RequestBody CreateOwnerDTO dto) throws BadRequestException {
        return ResponseEntity.ok(ApiResponseDTO.success("owner created successfully", ownerService.createOwner(dto)));
    }

    //     get all  owners by admin
    @Operation(
            summary = "get all  owners by admin",
            description = "get all  owner by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  owner by admin  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    private ResponseEntity<ApiResponseDTO> getAllOwners(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("Owners fetched successfully", ownerService.getAllOwners(pageable)));
    }

    //     get   owner by id
    @Operation(
            summary = "Owner fetched successfully",
            description = "Owner fetched successfully",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "  Owner fetched successfully successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/owner/{id}")
    private ResponseEntity<ApiResponseDTO> getOwnerById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseDTO.success("Owner fetched successfully", ownerService.getOwnerByID(id)));
    }

    //    search   owner by admin
    @Operation(
            summary = " search   owner by admin ",
            description = " search   owner by admin",
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
        return ResponseEntity.ok(ApiResponseDTO.success("Users fetched successfully", this.ownerService.search(pageable, q)));
    }
}
