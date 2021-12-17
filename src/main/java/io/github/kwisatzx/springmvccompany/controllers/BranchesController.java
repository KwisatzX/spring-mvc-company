package io.github.kwisatzx.springmvccompany.controllers;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.Branch;
import io.github.kwisatzx.springmvccompany.model.Employee;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class BranchesController {

    private final BranchRepository branches;
    private final EmployeeRepository employees;

    public BranchesController(BranchRepository branches,
                              EmployeeRepository employees) {
        this.branches = branches;
        this.employees = employees;
    }

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("employees")
    public Collection<Employee> getEmployeeList() {
        return employees.findByLastName("");
    }

    @GetMapping("/branches")
    public String listBranches(Model model) {
        Collection<Branch> results = branches.getAllBranches();
        model.addAttribute("branchList", results);
        return "branches/branchList";
    }

    @GetMapping("/branches/{branchId}")
    public String branchDetails(@PathVariable Long branchId, Model model) {
        Branch branch = this.branches.findById(branchId).get();
        model.addAttribute("branch", branch);
        return "branches/branchDetails";
    }

    @DeleteMapping("/branches/{branchId}")
    public String deleteBranch(@PathVariable Long branchId) {
        branches.deleteById(branchId);
        return "redirect:/branches";
    }

    @GetMapping("/branches/{branchId}/edit")
    public String initEditForm(@PathVariable Long branchId, Model model) {
        Branch branch = branches.findById(branchId)
                .orElseThrow(() -> new NotFoundException("Branch not found (id:" + branchId + ")"));
        model.addAttribute("branch", branch);
        return "branches/branchNewOrEdit";
    }

    @PostMapping("/branches/{branchId}/edit")
    public String editBranch(@PathVariable Long branchId,
                             @Valid @RequestBody Branch newBranch,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "branches/branchNewOrEdit";
        else {
            branches.findById(branchId).ifPresent(branch -> {
                branch.setBranch(newBranch);
                branches.save(branch);
            });
            return "redirect:/branches/" + branchId;
        }
    }

    @GetMapping("/branches/new")
    public String newBranchGet(Model model) {
        model.addAttribute("branch", new Branch());
        return "branches/branchNewOrEdit";
    }

    @PostMapping("/branches/new")
    public String newBranchPost(@Valid @ModelAttribute("branch") Branch branch, BindingResult bindingResult) {
        if (branches.existsByName(branch.getName()))
            bindingResult.rejectValue("name", "", "A Branch with that name already exists.");
        if (bindingResult.hasErrors()) return "branches/branchNewOrEdit";
        else {
            branches.save(branch);
            return "redirect:/branches/" + branch.getId();
        }
    }
}
