package rw.gov.rra.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rw.gov.rra.v1.audits.InitiatorAudit;

import java.time.Year;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "chassis_number", unique = true)
    private String chassisNumber;

    @Column(name = "manufacturer_company")
    private String manufactureCompany;

    @Column(name = "manufacturedYear")
    private Year manufacturedYear;

    @Column(name = "price")
    private Double price;

    @Column(name = "model_name")
    private String modelName;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToOne()
    @JoinColumn(name = "plate_id")
    private Plate plate;
}
