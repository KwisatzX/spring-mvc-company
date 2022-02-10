package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Collection<Employee> findDistinctByLastNameContainingIgnoreCase(String lastName);

    Collection<Employee> findAllBySuperiorId(Long id);

    Collection<Employee> findAllByBranchId(Long id);

    boolean existsByFirstNameAndLastNameAndBirthDay(String firstName, String lastName, LocalDate birthDay);
}
