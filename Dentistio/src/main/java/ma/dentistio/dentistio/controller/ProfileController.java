package ma.dentistio.dentistio.controller;

import ma.dentistio.dentistio.model.entity.Dentist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model) {
        Dentist dentist = getCurrentLoggedInDentist();
        model.addAttribute("dentist", dentist);
        return "profile";
    }

    private Dentist getCurrentLoggedInDentist() {
        // Implement this method to get the current logged-in dentist's information
        // For example, you might fetch it from the database based on the logged-in user's details
        return new Dentist(); // Replace with actual implementation
    }
}
