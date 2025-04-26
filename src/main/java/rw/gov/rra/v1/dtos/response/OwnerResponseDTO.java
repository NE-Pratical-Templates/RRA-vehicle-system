package rw.gov.rra.v1.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String nationalId;
    private String mobile;
    private LocalDate dob;
}
