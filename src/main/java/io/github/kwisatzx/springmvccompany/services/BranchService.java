package io.github.kwisatzx.springmvccompany.services;

import io.github.kwisatzx.springmvccompany.model.BranchSupplier;
import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface BranchService {

    Optional<Branch> findById(Long id);

    void deleteById(Long id);

    Branch save(Branch branch);

    List<Branch> getAllBranches();

    boolean existsByName(String name);

    List<BranchSupplier> getBranchSuppliers();

    Optional<Employee> findEmployeeById(Long id);

    Set<Employee> findEmployeesByIds(Set<Long> ids);

    Set<Client> findClientsByIds(Set<Long> ids);
}
