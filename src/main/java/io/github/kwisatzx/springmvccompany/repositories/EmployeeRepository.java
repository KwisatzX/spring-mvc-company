package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
