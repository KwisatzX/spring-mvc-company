package io.github.kwisatzx.springmvccompany.controllers.web;

import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class SuppliersController {

    private final SupplierService service;

    @GetMapping("/suppliers")
    public String listBranchSuppliers(Model model) {
        List<BranchSupplier> results = (List<BranchSupplier>) service.findAll();
        model.addAttribute("suppliers", results);
        return "branches/branchSupplierList";
    }
}
