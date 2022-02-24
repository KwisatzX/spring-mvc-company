package io.github.kwisatzx.springmvccompany.controllers.web;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.model.employee.validation.EmployeeValidator;
import io.github.kwisatzx.springmvccompany.services.BranchService;
import io.github.kwisatzx.springmvccompany.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Controller
public class EmployeesController {

    private final EmployeeService employeeService;
    private final BranchService branchService;

    @ModelAttribute("superiors")
    public Collection<Employee> getEmployeeList() {
        return employeeService.findAllByLastName("");
    }

    @ModelAttribute("branches")
    public Collection<Branch> getBranchList() {
        return branchService.getAllBranches();
    }

    @ModelAttribute("sex")
    public List<String> getSexList() {
        return List.of("M", "F");
    }

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.setValidator(new EmployeeValidator());
    }

    @GetMapping("/employees/find")
    public String initFindForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "empl/findEmployees";
    }

    @DeleteMapping("/employees/{empId}")
    public String deleteEmployee(@PathVariable Long empId) {
        employeeService.deleteById(empId);
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String processFindForm(Employee employee, BindingResult bindingResult, Model model) {
        if (employee.getLastName() == null) employee.setLastName("");

        Collection<Employee> results = employeeService.findAllByLastName(employee.getLastName());
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
        Employee employee = employeeService.findById(empId)
                .orElseThrow(NotFoundException::new);
        model.addAttribute("emp", employee);
        return "empl/employeeDetails";
    }

    @GetMapping("/employees/{empId}/edit")
    public String initEditForm(@PathVariable Long empId, Model model) {
        Employee employee = employeeService.findById(empId)
                .orElseThrow(NotFoundException::new);
        model.addAttribute("emp", employee);
        return "empl/employeeNewOrEdit";
    }

    @PostMapping("/employees/{empId}/edit")
    public String editEmployee(@PathVariable Long empId,
                               @Valid @ModelAttribute("emp") Employee newEmployee,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "empl/employeeNewOrEdit";
        else {
            employeeService.findById(empId).ifPresent(employee -> {
                employee.setEmployee(newEmployee);
                employeeService.save(employee);
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
    public String newEmployeePost(@Valid @ModelAttribute("emp") Employee emp, BindingResult result) {
        if (employeeService.existsByFirstNameAndLastNameAndBirthDay(
                emp.getFirstName(), emp.getLastName(), emp.getBirthDay())) {
            result.rejectValue(null, "", "Duplicate entry for " + emp + " born " + emp.getBirthDay());
        }
        if (result.hasErrors()) return "empl/employeeNewOrEdit";
        else {
            employeeService.save(emp);
            return "redirect:/employees/" + emp.getId();
        }
    }
}
