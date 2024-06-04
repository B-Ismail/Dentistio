package ma.dentistio.dentistio.repository;

import ma.dentistio.dentistio.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
}
