package io.github.kwisatzx.springmvccompany.services;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public interface EmployeeService {

    Collection<Employee> findAllByLastName(String lastName);

    Collection<Employee> findAll();

    Collection<Employee> findAllByBranchId(Long branchId);

    Collection<Employee> findAllBySuperiorId(Long superiorId);

    Collection<Employee> findAllById(Set<Long> ids);

    Optional<Employee> findById(Long id);

    Employee save(Employee employee);

    void deleteById(Long id);

    boolean existsByFirstNameAndLastNameAndBirthDay(String firstName, String lastName, LocalDate birthDay);

    Optional<Branch> findBranchById(Long id);
}
