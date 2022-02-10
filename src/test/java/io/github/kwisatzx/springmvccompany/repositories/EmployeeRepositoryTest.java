package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Test
    public void canAddEmployeeAndFindByLastName() {
        Branch branch = branchRepository.findById(1L).get();

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setSex('M');
        employee.setBirthDay(LocalDate.of(1980, 1, 1));
        employee.setBranch(branch);
        employee.setSalary(100.000);
        employeeRepository.save(employee);
        Collection<Employee> result = employeeRepository.findDistinctByLastNameContainingIgnoreCase("Smith");
        assertFalse(result.isEmpty());
        Employee resultEmployee = result.iterator().next();
        assertEquals("John", resultEmployee.getFirstName());
        assertEquals(100.000, resultEmployee.getSalary());
        assertEquals('M', resultEmployee.getSex());
    }
}
