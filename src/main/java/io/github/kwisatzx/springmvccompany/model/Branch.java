package io.github.kwisatzx.springmvccompany.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "branches")
public class Branch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long id;
    @Column(name = "branch_name")
    private String branchName;
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @JoinColumn(name = "mgr_start_date")
    private LocalDate mgrStartDate;


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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branch_name) {
        this.branchName = branch_name;
    }
}
