package ma.dentistio.dentistio.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int age;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paid = PaymentStatus.NO_INFO;

    @Enumerated(EnumType.STRING)
    private OperationStatus needsMoreOperations = OperationStatus.NO_INFO;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date firstVisitDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitDate;

    private String notes;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits;

    @PrePersist
    protected void onCreate() {
        firstVisitDate = new Date();
        lastVisitDate = new Date();
    }

    public Date getFirstVisitDate() {
        return firstVisitDate;
    }

    public void setFirstVisitDate(Date firstVisitDate) {
        this.firstVisitDate = firstVisitDate;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }
}
