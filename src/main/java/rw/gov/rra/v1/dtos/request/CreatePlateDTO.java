package rw.gov.rra.v1.dtos.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreatePlateDTO {
    @Pattern(
            regexp = "^R[A-Z]{2}\\s\\d{3}\\s[A-Z]$",
            message = "Plate number must follow the format like 'RAD 123 B'"
    )
    private String plateNumber;
}
