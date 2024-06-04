package ma.dentistio.dentistio.controller;

import ma.dentistio.dentistio.model.entity.Employee;
import ma.dentistio.dentistio.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public String listEmployees(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeRepository.findAll(pageable);
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/searchEmployee")
    public String searchEmployee(@RequestParam String search,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeRepository.findByFirstNameContainingOrLastNameContaining(search, search, pageable);
        model.addAttribute("employees", employees);
        model.addAttribute("search", search);
        return "employees";
    }

    @GetMapping("/viewEmployee/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "viewEmployee";
    }

    @GetMapping("/editEmployee/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "editEmployee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/addEmployeeForm")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }
}
