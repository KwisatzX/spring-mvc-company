package io.github.kwisatzx.springmvccompany.services.impl;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplierId;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.SupplierRepository;
import io.github.kwisatzx.springmvccompany.services.interfaces.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Component
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final BranchRepository branchRepository;

    @Override public Collection<BranchSupplier> findAll() {
        return (Collection<BranchSupplier>) supplierRepository.findAll();
    }

    @Override public Collection<BranchSupplier> findAllForBranch(Branch branch) {
        return (Collection<BranchSupplier>) supplierRepository.findAllByBranch(branch);
    }

    @Override public Collection<BranchSupplier> findAllForBranchId(Long branchId) {
        return findAllForBranch(branchRepository.findById(branchId).get());
    }

    @Override public Optional<BranchSupplier> findByName(String name) {
        return supplierRepository.findBySupplierName(name);
    }

    @Override public Optional<BranchSupplier> findByBranchAndName(Branch branch, String name) {
        return supplierRepository.findById(new BranchSupplierId(branch, name));
    }

    @Override public BranchSupplier save(BranchSupplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override public void delete(String name) {
        supplierRepository.deleteBySupplierName(name);
    }

    @Override public void delete(Branch branch, String name) {
        supplierRepository.deleteById(new BranchSupplierId(branch, name));
    }

    @Override public Optional<Branch> findBranchById(Long id) {
        return branchRepository.findById(id);
    }

    @Override public boolean existsById(BranchSupplierId id) {
        return supplierRepository.existsById(id);
    }
}
