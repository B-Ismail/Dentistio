package ma.dentistio.dentistio.repository;

import ma.dentistio.dentistio.model.entity.OperationStatus;
import ma.dentistio.dentistio.model.entity.Patient;
import ma.dentistio.dentistio.model.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    List<Patient> findByPaidFalse();
    List<Patient> findByNeedsMoreOperationsTrue();

    List<Patient> findByPaid(PaymentStatus paymentStatus);

    List<Patient> findByNeedsMoreOperations(OperationStatus operationStatus);
}
