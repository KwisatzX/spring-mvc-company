package io.github.kwisatzx.springmvccompany.controllers;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.Branch;
import io.github.kwisatzx.springmvccompany.model.Employee;
import io.github.kwisatzx.springmvccompany.model.EmployeeValidator;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
public class EmployeesController {

    private final EmployeeRepository employees;
    private final BranchRepository branches;

    public EmployeesController(EmployeeRepository employeeRepository,
                               BranchRepository branchRepository) {
        this.employees = employeeRepository;
        this.branches = branchRepository;
    }

    @ModelAttribute("superiors")
    public Collection<Employee> getEmployeeList() {
        return employees.findByLastName("");
    }

    @ModelAttribute("branches")
    public Collection<Branch> getBranchList() {
        return branches.getAllBranches();
    }

    @ModelAttribute("sex")
    public List<String> getSexList() {
        return List.of("M", "F");
    }

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.setValidator(new EmployeeValidator(employees));
    }

    @GetMapping("/employees/find")
    public String initFindForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "empl/findEmployees";
    }

    @DeleteMapping("/employees/{empId}")
    public String deleteEmployee(@PathVariable Long empId) {
        employees.deleteById(empId);
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String processFindForm(Employee employee, BindingResult bindingResult, Model model) {
        if (employee.getLastName() == null) employee.setLastName("");

        Collection<Employee> results = employees.findByLastName(employee.getLastName());
        if (results.isEmpty()) {
            bindingResult.rejectValue("lastName", "", "not found");
            return "empl/findEmployees";
        }
        if (results.size() == 1) {
            return "redirect:/employees/" + results.iterator().next().getId();
        } else {
            model.addAttribute("empList", results);
            return "empl/employeeList";
        }
    }

    @GetMapping("/employees/{empId}")
    public String empDetails(@PathVariable Long empId, Model model) {
        Employee employee = employees.findById(empId)
                .orElseThrow(() -> new NotFoundException("Employee not found (id:" + empId + ")"));
        model.addAttribute("emp", employee);
        return "empl/employeeDetails";
    }

    @GetMapping("/employees/{empId}/edit")
    public String initEditForm(@PathVariable Long empId, Model model) {
        Employee employee = employees.findById(empId)
                .orElseThrow(() -> new NotFoundException("Employee not found (id:" + empId + ")"));
        model.addAttribute("emp", employee);
        return "empl/employeeNewOrEdit";
    }

    @PostMapping("/employees/{empId}/edit")
    public String editEmployee(@PathVariable Long empId,
                               @Valid @ModelAttribute("emp") Employee newEmployee,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "empl/employeeNewOrEdit";
        else {
            employees.findById(empId).ifPresent(employee -> {
                employee.setEmployee(newEmployee);
                employees.save(employee);
            });
            return "redirect:/employees/" + empId;
        }
    }

    @GetMapping("/employees/new")
    public String newEmployeeGet(Model model) {
        model.addAttribute("emp", new Employee());
        return "empl/employeeNewOrEdit";
    }

    @PostMapping("/employees/new")
    public String newEmployeePost(@Valid @ModelAttribute("emp") Employee emp, BindingResult bindingResult) {
        if (employees.existsByFirstNameAndLastName(emp.getFirstName(), emp.getLastName())) {
            bindingResult.rejectValue("firstName", "", "Duplicate entry for " + emp);
        }
        if (bindingResult.hasErrors()) return "empl/employeeNewOrEdit";
        else {
            employees.save(emp);
            return "redirect:/employees/" + emp.getId();
        }
    }
}
