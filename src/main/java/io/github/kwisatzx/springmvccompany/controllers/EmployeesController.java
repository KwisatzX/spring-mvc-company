package io.github.kwisatzx.springmvccompany.controllers;

import io.github.kwisatzx.springmvccompany.model.Employee;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@Controller
public class EmployeesController {

    private final EmployeeRepository employeeRepository;

    public EmployeesController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees/find")
    public String initFindForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "empl/findEmployees";
    }

    @GetMapping("/employees")
    public String processFindForm(Employee employee, Model model) {
        if (employee.getLastName() == null) employee.setLastName("");

        Collection<Employee> results = employeeRepository.findByLastName(employee.getLastName());
        if (results.isEmpty()) {
            //TODO: error handling
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
        Employee employee = employeeRepository.findById(empId).get();
        model.addAttribute("emp", employee);
        return "empl/employeeDetails";
    }
}
