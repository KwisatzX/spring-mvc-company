package io.github.kwisatzx.springmvccompany.model.employee.validation;

import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeInputDto;
import io.github.kwisatzx.springmvccompany.services.interfaces.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@AllArgsConstructor
@Component
public class EmployeeInputDtoValidator implements Validator { //TODO test

    private final EmployeeService service;

    @Override public boolean supports(Class<?> clazz) {
        return EmployeeInputDto.class.equals(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        EmployeeInputDto dto = (EmployeeInputDto) target;
        if (!StringUtils.hasText(dto.firstName()))
            errors.rejectValue("firstName", "", "Field 'firstName' is empty or null");
        if (!StringUtils.hasText(dto.lastName()))
            errors.rejectValue("lastName", "", "Field 'lastName' is empty or null");
        if (dto.birthDay().isAfter(LocalDate.now().minusYears(18)))
            errors.rejectValue("birthDay", "", "Employee is under 18 years old");
        if (!(dto.sex() == 'm' || dto.sex() == 'M' || dto.sex() == 'f' || dto.sex() == 'F'))
            errors.rejectValue("sex", "", "Wrong sex");
        if (dto.salary() == 0) errors.rejectValue("salary", "", "Salary cannot be zero");
        if (service.findById(dto.superiorId()).isEmpty())
            errors.rejectValue("superiorId", "", "Invalid superiorId - not found");
        if (service.findBranchById(dto.branchId()).isEmpty())
            errors.rejectValue("branchId", "", "Invalid branchId - not found");
    }
}
