package ma.dentistio.dentistio.repository;

import ma.dentistio.dentistio.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatientId(Long patientId);
}
