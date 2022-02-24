package io.github.kwisatzx.springmvccompany.services.impl;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.ClientRepository;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import io.github.kwisatzx.springmvccompany.repositories.SupplierRepository;
import io.github.kwisatzx.springmvccompany.services.interfaces.BranchService;
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
    private final SupplierRepository supplierRepository;

    @Override public List<Branch> getAllBranches() {
        return branchRepository.getAllBranches();
    }

    @Override public Optional<Branch> findById(Long id) {
        return branchRepository.findById(id);
    }

    @Override public void deleteById(Long id) {
        branchRepository.deleteById(id);
    }

    @Override public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override public boolean existsByName(String name) {
        return branchRepository.existsByName(name);
    }

    @Override public List<BranchSupplier> getBranchSuppliers() {
        return (List<BranchSupplier>) supplierRepository.findAll();
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
