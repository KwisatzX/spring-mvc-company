package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.Branch;
import io.github.kwisatzx.springmvccompany.model.BranchSupplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BranchRepository extends CrudRepository<Branch, Long> {

    @Query("SELECT DISTINCT branch FROM Branch branch LEFT JOIN FETCH branch.clients LEFT JOIN FETCH branch.employees")
    @Transactional(readOnly = true)
    List<Branch> getAllBranches();

    boolean existsByName(String name);

    @Query("SELECT supplier FROM BranchSupplier supplier")
    @Transactional(readOnly = true)
    List<BranchSupplier> getBranchSuppliers();
}
