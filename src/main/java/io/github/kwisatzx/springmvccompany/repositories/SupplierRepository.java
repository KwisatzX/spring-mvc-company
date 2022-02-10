package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplierId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SupplierRepository extends CrudRepository<BranchSupplier, BranchSupplierId> {

    Optional<BranchSupplier> findBySupplierName(String supplierName);

    Iterable<BranchSupplier> findAllByBranch(Branch branch);

    void deleteBySupplierName(String supplierName);
}
