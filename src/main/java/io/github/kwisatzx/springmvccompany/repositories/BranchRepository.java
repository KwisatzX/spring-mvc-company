package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.Branch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BranchRepository extends CrudRepository<Branch, Long> {

    @Query("SELECT DISTINCT branch FROM Branch branch")
    @Transactional(readOnly = true)
    List<Branch> getAllBranches();
}
