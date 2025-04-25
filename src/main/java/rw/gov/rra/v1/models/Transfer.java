package rw.gov.rra.v1.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import rw.gov.rra.v1.audits.InitiatorAudit;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfers")
public class Transfer extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "issued_date")
    private LocalDateTime issuedDate;

    @OneToOne()
    @JoinColumn(name = "old_owner_id")
    private Owner oldOwner;

    @OneToOne()
    @JoinColumn(name = "new_owner_id")
    private Owner newOwner;

    @Column(name = "amount")
    private Double amount;
}
