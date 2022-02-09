package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BranchRepository extends CrudRepository<Branch, Long> {

    @Query("SELECT DISTINCT branch FROM Branch branch LEFT JOIN FETCH branch.clients LEFT JOIN FETCH branch.employees")
    @Transactional(readOnly = true)
    List<Branch> getAllBranches();

    boolean existsByName(String name);
}
