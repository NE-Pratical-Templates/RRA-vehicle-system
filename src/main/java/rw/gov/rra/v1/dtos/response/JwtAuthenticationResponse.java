package rw.gov.rra.v1.dtos.response;

import lombok.Getter;
import lombok.Setter;
import rw.gov.rra.v1.models.User;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;

    public JwtAuthenticationResponse(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}