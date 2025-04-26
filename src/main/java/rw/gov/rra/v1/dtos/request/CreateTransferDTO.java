package rw.gov.rra.v1.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferDTO {
    @Pattern(regexp = "^1\\d{15}$", message = "provide valid national ID")
    private String oldOwnerNationalID;

    @NotBlank
    @Email
    private String oldOwnerEmail;

    @Pattern(regexp = "^1\\d{15}$", message = "provide valid national ID")
    private String newOwnerNationalID;

    @NotBlank
    @Email
    private String newOwnerEmail;

    @NotBlank
    private String vehicleChassisNumber;
    @NotBlank
    @Pattern(
            regexp = "^R[A-Z]{2}\\s\\d{3}\\s[A-Z]$",
            message = "Plate number must follow the format like 'RAD 123 B'"
    )
    private String newPlate;

    private Double amount;

}
