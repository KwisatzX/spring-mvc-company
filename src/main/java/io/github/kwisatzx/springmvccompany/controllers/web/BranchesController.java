package io.github.kwisatzx.springmvccompany.controllers.web;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.services.BranchService;
import io.github.kwisatzx.springmvccompany.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@AllArgsConstructor
@Controller
public class BranchesController {

    private final BranchService branchService;
    private final EmployeeService employeeService;

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("employees")
    public Collection<Employee> getEmployeeList() {
        return employeeService.findAllByLastName("");
    }

    @GetMapping("/branches")
    public String listBranches(Model model) {
        Collection<Branch> results = branchService.getAllBranches();
        model.addAttribute("branchList", results);
        return "branches/branchList";
    }

    @GetMapping("/branches/{branchId}")
    public String branchDetails(@PathVariable Long branchId, Model model) {
        Branch branch = branchService.findById(branchId)
                .orElseThrow(NotFoundException::new);
        model.addAttribute("branch", branch);
        return "branches/branchDetails";
    }

    @DeleteMapping("/branches/{branchId}")
    public String deleteBranch(@PathVariable Long branchId) {
        branchService.deleteById(branchId);
        return "redirect:/branches";
    }

    @GetMapping("/branches/{branchId}/edit")
    public String initEditForm(@PathVariable Long branchId, Model model) {
        Branch branch = branchService.findById(branchId)
                .orElseThrow(NotFoundException::new);
        model.addAttribute("branch", branch);
        return "branches/branchNewOrEdit";
    }

    @PostMapping("/branches/{branchId}/edit")
    public String editBranch(@PathVariable Long branchId,
                             @Valid @RequestBody Branch newBranch,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "branches/branchNewOrEdit";
        else {
            branchService.findById(branchId).ifPresent(branch -> {
                branch.setBranch(newBranch);
                branchService.save(branch);
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
        if (branchService.existsByName(branch.getName()))
            bindingResult.rejectValue("name", "", "A Branch with that name already exists.");
        if (bindingResult.hasErrors()) return "branches/branchNewOrEdit";
        else {
            branchService.save(branch);
            return "redirect:/branches/" + branch.getId();
        }
    }
}
