package rw.gov.rra.v1.dtos.request;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.Year;

@Data

public class CreateVehicleDTO {
    private String chassisNumber;
    private String manufactureCompany;
    @PastOrPresent(message = "Manufactured year must be in the past or this year")
    private Year manufacturedYear;
    private Double price;
    private String modelName;
    @Pattern(
            regexp = "^R[A-Z]{2}\\s\\d{3}\\s[A-Z]$",
            message = "Plate number must follow the format like 'RAD 123 B'"
    )
    private  String plateNumber;
}
