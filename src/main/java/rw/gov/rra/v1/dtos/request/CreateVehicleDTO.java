package rw.gov.rra.v1.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.Year;

@Data

public class CreateVehicleDTO {
    @NotBlank
    private String chassisNumber;

    @NotBlank
    private String manufactureCompany;

    @NotBlank
    @PastOrPresent(message = "Manufactured year must be in the past or this year")
    private Year manufacturedYear;

    @NotBlank
    private Double price;

    @NotBlank
    private String modelName;

    @NotBlank
    @Pattern(
            regexp = "^R[A-Z]{2}\\s\\d{3}\\s[A-Z]$",
            message = "Plate number must follow the format like 'RAD 123 B'"
    )
    private String plateNumber;
}
