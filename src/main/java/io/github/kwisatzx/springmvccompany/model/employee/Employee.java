package io.github.kwisatzx.springmvccompany.model.employee;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name = "employees")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    @Column(name = "first_name")
    @NotEmpty
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty
    private String lastName;
    @Column(name = "birth_day")
    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"dd-MM-yyyy"})
    private LocalDate birthDay;
    private Character sex;
    private Double salary;
    @OneToOne
    @JoinColumn(name = "super_id")
    private Employee superior;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    public void setEmployee(Employee employee) {
        setFirstName(employee.getFirstName());
        setLastName(employee.getLastName());
        setBirthDay(employee.getBirthDay());
        setSex(employee.getSex());
        setSalary(employee.getSalary());
        setSuperior(employee.getSuperior());
        setBranch(employee.getBranch());
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
