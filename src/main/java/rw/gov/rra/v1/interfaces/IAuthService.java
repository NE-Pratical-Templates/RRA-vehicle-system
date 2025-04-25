package rw.gov.rra.v1.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import rw.gov.rra.v1.dtos.request.CreateUserDTO;
import rw.gov.rra.v1.dtos.request.LoginDTO;
import rw.gov.rra.v1.dtos.response.JwtAuthenticationResponse;
import rw.gov.rra.v1.models.User;

public interface IAuthService {
    User registerUser(@Valid CreateUserDTO dto);


    JwtAuthenticationResponse login(@Valid LoginDTO dto);

    void verifyAccount(String verificationCode);

    void initiateAccountVerification(@Email String email);


    void initiateResetPassword(@NotBlank @Email String email);

    void resetPassword(@NotBlank String email, @NotBlank String passwordResetCode, String newPassword);
}
