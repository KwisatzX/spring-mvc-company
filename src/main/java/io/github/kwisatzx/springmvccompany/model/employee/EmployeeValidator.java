package io.github.kwisatzx.springmvccompany.model.employee;

import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EmployeeValidator implements Validator {

    private final EmployeeRepository employees;

    public EmployeeValidator(EmployeeRepository employees) {
        this.employees = employees;
    }

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
