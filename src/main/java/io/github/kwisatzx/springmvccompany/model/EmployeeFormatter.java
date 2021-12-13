package io.github.kwisatzx.springmvccompany.model;

import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class EmployeeFormatter implements Formatter<Employee> {

    private final EmployeeRepository employees;

    public EmployeeFormatter(EmployeeRepository employees) {
        this.employees = employees;
    }

    @Override
    public String print(Employee employee, Locale locale) {
        return employee.toString();
    }

    @Override
    public Employee parse(String empToString, Locale locale) throws ParseException {
        Collection<Employee> employees = this.employees.findByLastName("");
        for (Employee emp : employees) {
            if (emp.toString().equals(empToString)) return emp;
        }
        throw new ParseException("employee not found: " + empToString, 0);
    }
}
