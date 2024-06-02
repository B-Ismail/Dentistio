package ma.dentistio.dentistio.controller;

import ma.dentistio.dentistio.model.entity.Patient;
import ma.dentistio.dentistio.model.entity.PaymentStatus;
import ma.dentistio.dentistio.model.entity.OperationStatus;
import ma.dentistio.dentistio.model.entity.Visit;
import ma.dentistio.dentistio.model.entity.VisitType;
import ma.dentistio.dentistio.repository.PatientRepository;
import ma.dentistio.dentistio.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/patients")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patients";
    }



    @PostMapping("/patients/deletePatient")
    public String deletePatient(@RequestParam Long id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }
    @PostMapping("/patients/cancelAddVisit")
    public String cancelAddVisit(@RequestParam Long id) {
        return "redirect:/patients/";
    }


    @GetMapping("/patients/editPatient/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient", patient);
        return "editPatient";
    }

    @PostMapping("/patients/updatePatient/{id}")
    public String updatePatient(@PathVariable Long id,
                                @ModelAttribute Patient patient,
                                @RequestParam("lastVisitDate") String lastVisitDateStr) throws ParseException {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setAge(patient.getAge());
        existingPatient.setPaid(patient.getPaid());
        existingPatient.setNeedsMoreOperations(patient.getNeedsMoreOperations());
        existingPatient.setLastVisitDate(dateFormat.parse(lastVisitDateStr));
        existingPatient.setNotes(patient.getNotes());
        patientRepository.save(existingPatient);
        return "redirect:/patients";
    }


    @GetMapping("/patients/viewPatient/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient", patient);
        List<Visit> visits = visitRepository.findByPatientId(id);
        model.addAttribute("visits", visits);
        model.addAttribute("visitTypes", VisitType.values()); // Ensure visitTypes are available
        return "viewPatient";
    }

    @GetMapping("/patients/searchPatient")
    public String searchPatient(@RequestParam String search, Model model) {
        List<Patient> patients = patientRepository.findByFirstNameContainingOrLastNameContaining(search, search);
        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/patients/filterPatient")
    public String filterPatient(@RequestParam String filter, Model model) {
        List<Patient> patients;
        if (filter.equals("unpaid")) {
            patients = patientRepository.findByPaid(PaymentStatus.UNPAID);
        } else if (filter.equals("needsMoreOperations")) {
            patients = patientRepository.findByNeedsMoreOperations(OperationStatus.NEEDS_MORE_OPERATIONS);
        } else {
            patients = patientRepository.findAll();
        }
        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/patients/filterByDate")
    public String filterByDate(@RequestParam String dateOrder, Model model) {
        List<Patient> patients = patientRepository.findAll();
        if (dateOrder.equals("asc")) {
            patients.sort(Comparator.comparing(Patient::getLastVisitDate));
        } else {
            patients.sort(Comparator.comparing(Patient::getLastVisitDate).reversed());
        }
        model.addAttribute("patients", patients);
        return "patients";
    }



    @PostMapping("/patients/addVisit")
    public String addVisit(@RequestParam Long patientId, @RequestParam VisitType visitType, @RequestParam double price) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        Visit visit = new Visit();
        visit.setPatient(patient);
        visit.setVisitType(visitType);
        visit.setPrice(price);
        visit.setVisitDate(new Date());
        visitRepository.save(visit);
        patient.setLastVisitDate(new Date());
        patientRepository.save(patient);
        return "redirect:/patients/viewPatient/" + patientId;
    }



    @PostMapping("/patients/deleteVisit")
    public String deleteVisit(@RequestParam Long visitId, @RequestParam Long patientId) {
        visitRepository.deleteById(visitId);
        return "redirect:/patients/viewPatient/" + patientId;
    }


    @PostMapping("/patients/updateVisit")
    public String updateVisit(@RequestParam Long visitId, @RequestParam Long patientId,
                              @RequestParam VisitType visitType, @RequestParam Date visitDate,
                              @RequestParam double price) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new IllegalArgumentException("Invalid visit Id:" + visitId));
        visit.setVisitType(visitType);
        visit.setVisitDate(visitDate);
        visit.setPrice(price);
        visitRepository.save(visit);
        return "redirect:/patients/viewPatient/" + patientId;
    }


    @GetMapping("/patients/addPatientForm")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatientForm";
    }

    @PostMapping("/patients/addPatient")
    public String addPatient(@ModelAttribute Patient patient) {
        patient.setFirstVisitDate(new Date());
        patient.setLastVisitDate(new Date());
        patient.setPaid(PaymentStatus.NO_INFO);
        patient.setNeedsMoreOperations(OperationStatus.NO_INFO);
        Patient savedPatient = patientRepository.save(patient);
        return "redirect:/patients/viewPatient/" + savedPatient.getId();
    }


}
