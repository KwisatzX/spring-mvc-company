package io.github.kwisatzx.springmvccompany.model.supplier;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchSupplierId implements Serializable {
    private Branch branch;
    private String supplierName;
}
