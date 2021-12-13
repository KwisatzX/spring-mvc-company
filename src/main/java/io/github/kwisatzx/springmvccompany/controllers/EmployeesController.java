package io.github.kwisatzx.springmvccompany.controllers;

import io.github.kwisatzx.springmvccompany.model.Branch;
import io.github.kwisatzx.springmvccompany.model.Employee;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class EmployeesController {

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    public EmployeesController(EmployeeRepository employeeRepository,
                               BranchRepository branchRepository) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
    }

    @ModelAttribute("superiors")
    public Collection<Employee> getEmployeeList() {
        return employeeRepository.findByLastName("");
    }

    @ModelAttribute("branches")
    public Collection<Branch> getBranchList() {
        return branchRepository.getAllBranches();
    }

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/employees/find")
    public String initFindForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "empl/findEmployees";
    }

    @GetMapping("/employees")
    public String processFindForm(Employee employee, BindingResult bindingResult, Model model) {
        if (employee.getLastName() == null) employee.setLastName("");

        Collection<Employee> results = employeeRepository.findByLastName(employee.getLastName());
        if (results.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "not found");
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
    public String empDetails(@PathVariable("empId") Long empId, Model model) {
        Employee employee = employeeRepository.findById(empId);
        model.addAttribute("emp", employee);
        return "empl/employeeDetails";
    }

    @GetMapping("/employees/new")
    public String newEmployeeGet(Model model) {
        model.addAttribute("emp", new Employee());
        return "empl/employeeNewOrEdit";
    }

    @PostMapping("/employees/new")
    public String newEmployeePost(@Valid @ModelAttribute("emp") Employee emp, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "empl/employeeNewOrEdit";
        else {
            employeeRepository.save(emp);
            return "redirect:/employees/" + emp.getId();
        }
    }
}
