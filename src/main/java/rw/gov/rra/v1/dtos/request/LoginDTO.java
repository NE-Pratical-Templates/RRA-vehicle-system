package rw.gov.rra.v1.dtos.request;

import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
public class LoginDTO {

    @NotBlank
    @Email
    private  String email;

    @NotBlank
    private  String password;
}

