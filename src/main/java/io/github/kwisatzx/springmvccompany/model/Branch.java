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
    private LocalDate mgr_start_date;


    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager_id) {
        this.manager = manager_id;
    }

    public LocalDate getMgr_start_date() {
        return mgr_start_date;
    }

    public void setMgr_start_date(LocalDate mgr_start_date) {
        this.mgr_start_date = mgr_start_date;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branch_name) {
        this.branchName = branch_name;
    }
}
