package ma.dentistio.dentistio;

import ma.dentistio.dentistio.model.entity.Dentist;
import ma.dentistio.dentistio.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DentistioApplication implements CommandLineRunner {

    @Autowired
    private DentistRepository dentistRepository;

    public static void main(String[] args) {
        SpringApplication.run(DentistioApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Add initial dentists data
        Dentist dentist1 = new Dentist();
        dentist1.setName("Dr. Alice Smith");
        dentist1.setSpecialty("Orthodontics");
        dentist1.setYearsOfExperience(15);
        dentist1.setEmail("alice@example.com");
        dentist1.setPhoneNumber("123-456-7890");
        dentist1.setNotes("Expert in braces and alignment.");

        Dentist dentist2 = new Dentist();
        dentist2.setName("Dr. Bob Jones");
        dentist2.setSpecialty("Pediatric Dentistry");
        dentist2.setYearsOfExperience(10);
        dentist2.setEmail("bob@example.com");
        dentist2.setPhoneNumber("234-567-8901");
        dentist2.setNotes("Specializes in children's dental care.");

        dentistRepository.save(dentist1);
        dentistRepository.save(dentist2);
    }
}
