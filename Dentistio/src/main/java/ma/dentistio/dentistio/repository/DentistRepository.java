package ma.dentistio.dentistio.repository;

import ma.dentistio.dentistio.model.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
}
