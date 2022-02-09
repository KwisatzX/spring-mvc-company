package io.github.kwisatzx.springmvccompany.model.supplier;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@IdClass(BranchSupplierId.class)
@Table(name = "branch_suppliers")
public class BranchSupplier implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Id
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "supply_type")
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchSupplier that = (BranchSupplier) o;

        if (!branch.equals(that.branch)) return false;
        return supplierName.equals(that.supplierName);
    }

    @Override
    public int hashCode() {
        int result = branch.hashCode();
        result = 31 * result + supplierName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return supplierName;
    }
}