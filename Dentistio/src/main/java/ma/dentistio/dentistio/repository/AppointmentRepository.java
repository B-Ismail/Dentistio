package ma.dentistio.dentistio.repository;

import ma.dentistio.dentistio.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
