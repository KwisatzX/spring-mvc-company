package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.IncorrectEntityException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeGetDto;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeInputDto;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeModelMapper;
import io.github.kwisatzx.springmvccompany.model.employee.validation.EmployeeInputDtoValidator;
import io.github.kwisatzx.springmvccompany.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class EmployeesRestController {

    private final EmployeeService service;
    private final EmployeeModelMapper mapper;

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setValidator(new EmployeeInputDtoValidator());
    }

    @GetMapping("/employees")
    public Collection<EmployeeGetDto> getEmployees() { //TODO pagination
        return service.findAll().stream().map(mapper::employeeToEmployeeGetDto).toList();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeGetDto> getEmployee(@PathVariable Long id) {
        Optional<Employee> result = service.findById(id);
        if (result.isPresent()) return ResponseEntity.ok(mapper.employeeToEmployeeGetDto(result.get()));
        else throw new NotFoundException();
    }

    @Transactional
    @PutMapping(value = "/employees/{id}/edit")
    public ResponseEntity<Void> editEmployee(@PathVariable Long id,
                                             @Valid @RequestBody EmployeeInputDto body,
                                             BindingResult result) {
        if (result.hasErrors()) throw new IncorrectEntityException();
        else {
            Employee newEmployee = mapper.employeeInputDtoToEmployee(body);
            Optional<Employee> dbEmployee = service.findById(id);
            if (dbEmployee.isPresent()) {
                dbEmployee.get().setEmployee(newEmployee);
                return ResponseEntity.noContent().location(URI.create("/employees/" + id)).build();
            } else {
                newEmployee.setId(id);
                Employee savedEmployee = service.save(newEmployee);
                return ResponseEntity.created(URI.create("/employees/" + savedEmployee.getId())).build();
            }
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/employees/new")
    public ResponseEntity<Void> newEmployee(@Valid @RequestBody EmployeeInputDto body, BindingResult result) {
        if (result.hasErrors()) throw new IncorrectEntityException(); //add error list
        if (service.existsByFirstNameAndLastNameAndBirthDay(body.firstName(), body.lastName(), body.birthDay())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //TODO exception?
        } else {
            Employee saved = service.save(mapper.employeeInputDtoToEmployee(body));
            return ResponseEntity.created(URI.create("/employees/" + saved.getId())).build();
        }
    }
}
