package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.DuplicateException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.InvalidEntityException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeGetDto;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeInputDto;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeModelMapper;
import io.github.kwisatzx.springmvccompany.model.employee.validation.EmployeeInputDtoValidator;
import io.github.kwisatzx.springmvccompany.services.interfaces.EmployeeService;
import lombok.AllArgsConstructor;
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
    private final EmployeeInputDtoValidator validator;

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setValidator(validator);
    }

    @GetMapping("/employees")
    public ResponseEntity<Collection<EmployeeGetDto>> getEmployees() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::employeeToEmployeeGetDto).toList());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeGetDto> getEmployee(@PathVariable Long id) {
        Optional<Employee> result = service.findById(id);
        if (result.isPresent()) return ResponseEntity.ok(mapper.employeeToEmployeeGetDto(result.get()));
        else throw new NotFoundException();
    }

    @GetMapping("/branches/{id}/employees")
    public ResponseEntity<Collection<EmployeeGetDto>> getEmployeesForBranch(@PathVariable Long id) {
        if (service.findBranchById(id).isEmpty()) throw new NotFoundException();
        return ResponseEntity.ok(service.findAllByBranchId(id).stream().map(mapper::employeeToEmployeeGetDto).toList());
    }

    @Transactional
    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<EmployeeGetDto> editEmployee(@PathVariable Long id,
                                                       @RequestBody @Valid EmployeeInputDto body,
                                                       BindingResult result) throws InvalidEntityException {
        if (result.hasErrors()) throw new InvalidEntityException(result);
        Employee requestEmployee = mapper.employeeInputDtoToEmployee(body);
        Optional<Employee> byId = service.findById(id);
        if (byId.isPresent()) {
            byId.get().setEmployee(requestEmployee);
            return ResponseEntity.noContent().location(URI.create("/employees/" + id)).build();
        } else {
            requestEmployee.setId(id);
            Employee saved = service.save(requestEmployee);
            return ResponseEntity.created(URI.create("/employees/" + saved.getId()))
                    .body(mapper.employeeToEmployeeGetDto(saved));
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeGetDto> newEmployee(@RequestBody @Valid EmployeeInputDto body, BindingResult result)
            throws InvalidEntityException {
        if (result.hasErrors()) throw new InvalidEntityException(result);
        if (service.existsByFirstNameAndLastNameAndBirthDay(body.firstName(), body.lastName(), body.birthDay())) {
            throw new DuplicateException("Employee with name '" + body.firstName() + " " + body.lastName()
                                                 + "' and birthday '" + body.birthDay() + "' already exists");
        } else {
            Employee saved = service.save(mapper.employeeInputDtoToEmployee(body));
            return ResponseEntity.created(URI.create("/employees/" + saved.getId()))
                    .body(mapper.employeeToEmployeeGetDto(saved));
        }
    }
}
