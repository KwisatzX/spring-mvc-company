package io.github.kwisatzx.springmvccompany.model.branch.validation;

import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchInputDto;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.services.interfaces.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class BranchInputDtoValidator implements Validator {

    private final BranchService service;

    @Override public boolean supports(Class<?> clazz) {
        return BranchInputDto.class.equals(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        BranchInputDto dto = (BranchInputDto) target;

        if (!StringUtils.hasText(dto.name()))
            errors.rejectValue("name", "", "Name is null or empty");
        if (service.findEmployeeById(dto.managerId()).isEmpty())
            errors.rejectValue("managerId", "", "Invalid managerId - not found");
        validateClientSet(dto, errors);
        validateEmployeeSet(dto, errors);
    }

    private void validateClientSet(BranchInputDto dto, Errors errors) {
        Set<Client> foundClients = service.findClientsByIds(dto.clientIds());
        Set<Long> foundClientIds = foundClients.stream().map(Client::getId).collect(Collectors.toSet());
        if (!foundClientIds.equals(dto.clientIds())) {
            Set<Long> missingClients = new HashSet<>(dto.clientIds());
            missingClients.removeAll(foundClientIds);
            String errorMessage = "Clients with following ids not found in database: " + missingClients;
            errors.rejectValue("clientIds", "", errorMessage);
        }
    }

    private void validateEmployeeSet(BranchInputDto dto, Errors errors) {
        Set<Employee> foundEmployees = service.findEmployeesByIds(dto.employeeIds());
        Set<Long> foundEmployeeIds = foundEmployees.stream().map(Employee::getId).collect(Collectors.toSet());
        if (!foundEmployeeIds.equals(dto.employeeIds())) {
            Set<Long> missingEmployees = new HashSet<>(dto.employeeIds());
            missingEmployees.removeAll(foundEmployeeIds);
            String errorMessage = "Employees with following ids not found in database: " + missingEmployees;
            errors.rejectValue("employeeIds", "", errorMessage);
        }
    }
}
