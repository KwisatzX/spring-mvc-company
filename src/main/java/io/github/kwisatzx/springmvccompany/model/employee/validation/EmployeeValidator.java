package io.github.kwisatzx.springmvccompany.model.employee.validation;

import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
public class EmployeeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee employee = (Employee) target;

        if (!StringUtils.hasLength(employee.getFirstName().trim())) {
            errors.rejectValue("firstName", "", "Name cannot be empty or blank");
        }

        if (!StringUtils.hasLength(employee.getLastName().trim())) {
            errors.rejectValue("lastName", "", "Name cannot be empty or blank");
        }
    }
}
