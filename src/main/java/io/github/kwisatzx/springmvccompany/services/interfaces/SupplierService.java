package io.github.kwisatzx.springmvccompany.services.interfaces;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplierId;

import java.util.Collection;
import java.util.Optional;

public interface SupplierService {

    Collection<BranchSupplier> findAll();

    Collection<BranchSupplier> findAllForBranch(Branch branch);

    Collection<BranchSupplier> findAllForBranchId(Long branchId);

    Optional<BranchSupplier> findByName(String name);

    Optional<BranchSupplier> findByBranchAndName(Branch branch, String name);

    BranchSupplier save(BranchSupplier supplier);

    void delete(String name);

    void delete(Branch branch, String name);

    Optional<Branch> findBranchById(Long id);

    boolean existsById(BranchSupplierId id);
}
