package io.github.kwisatzx.springmvccompany.model.branch;

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchSupplier that = (BranchSupplier) o;

        if (!branch.equals(that.branch)) return false;
        return supplier.equals(that.supplier);
    }

    @Override
    public int hashCode() {
        int result = branch.hashCode();
        result = 31 * result + supplier.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return supplier;
    }
}
