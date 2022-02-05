package io.github.kwisatzx.springmvccompany.model.employee.validation;

import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeInputDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EmployeeInputDtoValidator implements Validator {

    @Override public boolean supports(Class<?> clazz) {
        return EmployeeInputDto.class.equals(clazz);
    }

    @Override public void validate(Object target, Errors errors) {

    }
}
