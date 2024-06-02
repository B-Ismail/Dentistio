package ma.dentistio.dentistio.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import ma.dentistio.dentistio.model.entity.VisitType;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private VisitType visitType;

    private Date visitDate;

    private double price;

    @PrePersist
    protected void onCreate() {
        visitDate = new Date();
    }
}
