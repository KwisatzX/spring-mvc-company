package io.github.kwisatzx.springmvccompany.services;

import io.github.kwisatzx.springmvccompany.model.BranchSupplier;
import io.github.kwisatzx.springmvccompany.model.Client;
import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.ClientRepository;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Component
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Override public List<Branch> getAllBranches() {
        return branchRepository.getAllBranches();
    }

    @Override public boolean existsByName(String name) {
        return branchRepository.existsByName(name);
    }

    @Override public List<BranchSupplier> getBranchSuppliers() {
        return branchRepository.getBranchSuppliers();
    }

    @Override public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override public Set<Employee> findEmployeesByIds(Set<Long> ids) {
        return (Set<Employee>) employeeRepository.findAllById(ids);
    }

    @Override public Set<Client> findClientsByIds(Set<Long> ids) {
        return (Set<Client>) clientRepository.findAllById(ids);
    }
}
