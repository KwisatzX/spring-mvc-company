package io.github.kwisatzx.springmvccompany.controllers;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.IncorrectEntityException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.Employee;
import io.github.kwisatzx.springmvccompany.model.EmployeeValidator;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class EmployeesRestController {

    private final EmployeeRepository employees;

    public EmployeesRestController(EmployeeRepository employees) {
        this.employees = employees;
    }

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setValidator(new EmployeeValidator(employees));
    }

    @GetMapping("/employees")
    public Collection<Employee> getEmployees() {
        return employees.findByLastName("");
    }

    @GetMapping("/employees/{empId}")
    public Employee getEmployee(@PathVariable Long empId) {
        return employees.findById(empId)
                .orElseThrow(() -> new NotFoundException("Employee not found (id:" + empId + ")"));
    }

    @RequestMapping(value = "/employees/{empId}/edit", method = {RequestMethod.POST, RequestMethod.PUT})
    public String editEmployee(@PathVariable Long empId, @Valid @RequestBody Employee employeeReq) {
        employees.findById(empId).ifPresentOrElse(employee -> employee.setEmployee(employeeReq), () -> {
            employeeReq.setId(empId);
            employees.save(employeeReq);
        });
        return "redirect:/api/employees/" + empId;
    }

    @DeleteMapping("/employees/{empId}")
    public void deleteEmployee(@PathVariable Long empId) {
        employees.deleteById(empId);
    }

    @PostMapping("/employees/new")
    public Employee newEmployee(@Valid @RequestBody Employee employeeReq) { //TODO: change to binding result?
        employees.save(employeeReq);
        if (employees.existsByFirstNameAndLastName(employeeReq.getFirstName(), employeeReq.getLastName()))
            return employeeReq;
        else throw new IncorrectEntityException();
    }
}
