package ma.dentistio.dentistio.services;

import ma.dentistio.dentistio.model.entity.Dentist;
import ma.dentistio.dentistio.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistService {

    @Autowired
    private DentistRepository dentistRepository;

    public List<Dentist> findAllDentists() {
        return dentistRepository.findAll();
    }

    public Optional<Dentist> findDentistById(Long id) {
        return dentistRepository.findById(id);
    }

    public Dentist saveDentist(Dentist dentist) {
        return dentistRepository.save(dentist);
    }
}
