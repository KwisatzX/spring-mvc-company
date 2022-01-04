package io.github.kwisatzx.springmvccompany.controllers;

import io.github.kwisatzx.springmvccompany.model.BranchSupplier;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SuppliersController {

    private final BranchRepository branchRepository;

    public SuppliersController(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @GetMapping("/suppliers")
    public String listBranchSuppliers(Model model) {
        List<BranchSupplier> results = branchRepository.getBranchSuppliers();
        model.addAttribute("suppliers", results);
        return "branches/branchSupplierList";
    }
}
