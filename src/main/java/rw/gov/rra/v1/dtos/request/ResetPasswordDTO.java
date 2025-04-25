package rw.gov.rra.v1.dtos.request;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import rw.gov.rra.v1.validators.ValidPassword;

@Getter
public class ResetPasswordDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String passwordResetCode;

    @ValidPassword
    private String newPassword;
}
