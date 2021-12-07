package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.Branch;
import org.springframework.data.repository.CrudRepository;

public interface BranchRepository extends CrudRepository<Branch, Long> {
}
