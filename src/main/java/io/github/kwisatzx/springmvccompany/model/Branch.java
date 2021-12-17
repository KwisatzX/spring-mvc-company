package io.github.kwisatzx.springmvccompany.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
@Entity
@Table(name = "branches")
public class Branch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long id;
    @Column(name = "branch_name")
    @NotEmpty
    private String name;
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @JoinColumn(name = "mgr_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate mgrStartDate;
    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER)
    private Set<Client> clients;
    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager_id) {
        this.manager = manager_id;
    }

    public LocalDate getMgrStartDate() {
        return mgrStartDate;
    }

    public void setMgrStartDate(LocalDate mgr_start_date) {
        this.mgrStartDate = mgr_start_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String branch_name) {
        this.name = branch_name;
    }

    public void setBranch(Branch branch) {
        setName(branch.getName());
        setManager(branch.getManager());
        setMgrStartDate(branch.getMgrStartDate());
    }

    public Set<Client> getClients() {
        if (clients == null) clients = new HashSet<>();
        return Collections.unmodifiableSet(clients);
    }

    public Set<Employee> getEmployees() {
        if (employees == null) employees = new HashSet<>();
        return Collections.unmodifiableSet(employees);
    }

    @JsonIgnore
    public String getClientsDisplayString() {
        return getClients().stream().map(Client::toString).collect(Collectors.joining(", "));
    }

    @JsonIgnore
    public String getEmployeesDisplayString() {
        return getEmployees().stream().map(Employee::toString).collect(Collectors.joining(", "));
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Branch branch = (Branch) o;

        return id.equals(branch.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }
}
