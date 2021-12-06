package io.github.kwisatzx.springmvccompany.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "branch_suppliers")
public class BranchSupplier implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Id
    @Column(name = "supplier_name")
    private String supplier;
    @Column(name = "supply_type")
    private String type;
}
