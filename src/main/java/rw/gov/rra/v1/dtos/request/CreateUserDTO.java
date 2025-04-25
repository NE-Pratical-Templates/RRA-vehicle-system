package rw.gov.rra.v1.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import rw.gov.rra.v1.validators.ValidPassword;

import java.time.LocalDate;

@Data
public class CreateUserDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @ValidPassword
    private String password;
    @Pattern(regexp = "^(078|079|072|073)\\d{7}$", message = "Phone number must be a valid Rwandan number")
    private String mobile;
    @Pattern(regexp = "^1\\d{15}$", message = "provide valid national ID")
    private String nationalId;
    @NotNull
    @PastOrPresent(message = "Date of birth should be in the past")
    @NotNull(message = "Date of birth should not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
}
