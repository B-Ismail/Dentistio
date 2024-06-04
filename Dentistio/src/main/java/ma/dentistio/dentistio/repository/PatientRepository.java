package ma.dentistio.dentistio.repository;

import ma.dentistio.dentistio.model.entity.OperationStatus;
import ma.dentistio.dentistio.model.entity.Patient;
import ma.dentistio.dentistio.model.entity.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    List<Patient> findByPaidFalse();
    List<Patient> findByNeedsMoreOperationsTrue();

    List<Patient> findByPaid(PaymentStatus paymentStatus);

    List<Patient> findByNeedsMoreOperations(OperationStatus operationStatus);
    Page<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
    Page<Patient> findByPaid(PaymentStatus paymentStatus, Pageable pageable);
    Page<Patient> findByNeedsMoreOperations(OperationStatus operationStatus, Pageable pageable);
    Page<Patient> findAllByOrderByLastVisitDateAsc(Pageable pageable);
    Page<Patient> findAllByOrderByLastVisitDateDesc(Pageable pageable);
    long countByLastVisitDateAfter(Date date);
    long countByLastVisitDateBetween(Date startDate, Date endDate);

    long countByPaid(PaymentStatus paymentStatus);
    long countByNeedsMoreOperations(OperationStatus operationStatus);
}
