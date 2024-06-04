package ma.dentistio.dentistio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ma.dentistio.dentistio.model.entity.PaymentStatus;
import ma.dentistio.dentistio.model.entity.OperationStatus;

import ma.dentistio.dentistio.repository.PatientRepository;

import java.util.Calendar;
import java.util.Date;

@Controller
    public class HomeController {
        @Autowired
        private PatientRepository patientRepository;

        @GetMapping("/")
        public String home(Model model) {
            // Total patients
            long totalPatients = patientRepository.count();


            // Upcoming visits (date after current date)
            // Get today's date
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startOfDay = calendar.getTime();

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endOfDay = calendar.getTime();
            long upcomingVisits = patientRepository.countByLastVisitDateBetween(startOfDay, endOfDay);

            // Paid patients
            long paidPatients = patientRepository.countByPaid(PaymentStatus.PAID);

            // Unpaid patients
            long unpaidPatients = patientRepository.countByPaid(PaymentStatus.UNPAID);

            // Patients needing more operations
            long patientsNeedingMoreOperations = patientRepository.countByNeedsMoreOperations(OperationStatus.NEEDS_MORE_OPERATIONS);

            // Patients with no information
            long patientsNoInfo = patientRepository.countByPaid(PaymentStatus.NO_INFO);

            model.addAttribute("totalPatients", totalPatients);
            model.addAttribute("upcomingVisits", upcomingVisits);
            model.addAttribute("paidPatients", paidPatients);
            model.addAttribute("unpaidPatients", unpaidPatients);
            model.addAttribute("patientsNeedingMoreOperations", patientsNeedingMoreOperations);
            model.addAttribute("patientsNoInfo", patientsNoInfo);

            return "home";
        }
    }
