package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.Branch;
import io.github.kwisatzx.springmvccompany.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Employee result = employeeRepository.findByLastName("Smith");
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals(100.000, result.getSalary());
        assertEquals('M', result.getSex());
    }
}
