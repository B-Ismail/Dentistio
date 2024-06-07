package ma.dentistio.dentistio.controller;

import ma.dentistio.dentistio.model.entity.Dentist;
import ma.dentistio.dentistio.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final DentistRepository dentistRepository;

    @Autowired
    public ProfileController(DentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @GetMapping("/uprofile")
    public String profile(Model model) {
        Dentist dentist = dentistRepository.findAll().get(0); // You can replace this logic to get the current logged-in dentist
        String[] nameParts = dentist.getName().split(" ");
        model.addAttribute("dentist", dentist);
        model.addAttribute("firstName", nameParts.length > 0 ? nameParts[0] : "");
        model.addAttribute("lastName", nameParts.length > 1 ? nameParts[1] : "");
        return "profile";
    }

    private Dentist getCurrentLoggedInDentist() {
        // Implement this method to get the current logged-in dentist's information
        // For example, you might fetch it from the database based on the logged-in user's details
        return new Dentist(); // Replace with actual implementation
    }
}
