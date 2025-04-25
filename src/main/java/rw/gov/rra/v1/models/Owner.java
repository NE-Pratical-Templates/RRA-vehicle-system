package rw.gov.rra.v1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import rw.gov.rra.v1.audits.InitiatorAudit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "nationalId", unique = true, nullable = false)
    private String nationalId;

    @Column(name = "mobile", unique = true, nullable = false)
    private String mobile;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Plate> plates = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<>();

}
