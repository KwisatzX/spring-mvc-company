package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface EmployeeRepository extends Repository<Employee, Long> {

    @Query("SELECT DISTINCT employee FROM Employee employee WHERE employee.lastName LIKE :lastName%")
    @Transactional(readOnly = true)
    Collection<Employee> findByLastName(@Param("lastName") String lastName);

    Employee findById(Long id);

    void save(Employee employee);
}
