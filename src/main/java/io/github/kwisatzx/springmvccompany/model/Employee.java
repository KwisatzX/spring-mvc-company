package io.github.kwisatzx.springmvccompany.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    private String sex;
    private Double salary;
    @OneToOne
    @JoinColumn(name = "super_id")
    private Employee superior;
    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birth_day) {
        this.birthDay = birth_day;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Employee getSuperior() {
        return superior;
    }

    public void setSuperior(Employee super_id) {
        this.superior = super_id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch_id) {
        this.branch = branch_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }
}
