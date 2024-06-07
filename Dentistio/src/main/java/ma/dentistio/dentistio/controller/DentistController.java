package ma.dentistio.dentistio.controller;

import ma.dentistio.dentistio.model.entity.Dentist;
import ma.dentistio.dentistio.repository.DentistRepository;
import ma.dentistio.dentistio.services.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DentistController {

    private final DentistRepository dentistRepository;

    @Autowired
    public DentistController(DentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        // Example: Fetching the first dentist in the repository
        Dentist dentist = dentistRepository.findById(1L).orElse(new Dentist());
        model.addAttribute("dentist", dentist);
        return "profile";
    }
}
