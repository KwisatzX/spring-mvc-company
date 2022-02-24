package io.github.kwisatzx.springmvccompany.services.impl;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import io.github.kwisatzx.springmvccompany.services.interfaces.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Component
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    @Override public Collection<Employee> findAllByLastName(String lastName) {
        return employeeRepository.findDistinctByLastNameContainingIgnoreCase(lastName);
    }

    @Override public Collection<Employee> findAll() {
        return (Collection<Employee>) employeeRepository.findAll();
    }

    @Override public Collection<Employee> findAllByBranchId(Long branchId) {
        return employeeRepository.findAllByBranchId(branchId);
    }

    @Override public Collection<Employee> findAllBySuperiorId(Long superiorId) {
        return employeeRepository.findAllBySuperiorId(superiorId);
    }

    @Override public Collection<Employee> findAllById(Set<Long> ids) {
        return (Collection<Employee>) employeeRepository.findAllById(ids);
    }

    @Override public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existsByFirstNameAndLastNameAndBirthDay(String firstName, String lastName, LocalDate birthDay) {
        return employeeRepository.existsByFirstNameAndLastNameAndBirthDay(firstName, lastName, birthDay);
    }

    @Override public Optional<Branch> findBranchById(Long id) {
        return branchRepository.findById(id);
    }
}
