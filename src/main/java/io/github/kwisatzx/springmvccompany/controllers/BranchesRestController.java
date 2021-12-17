package io.github.kwisatzx.springmvccompany.controllers;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.Branch;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BranchesRestController {

    private final BranchRepository branches;

    public BranchesRestController(BranchRepository branches) {
        this.branches = branches;
    }

    @GetMapping("/branches")
    public List<Branch> listBranches() {
        return branches.getAllBranches();
    }

    @GetMapping("/branches/{branchId}")
    public Branch branchDetails(@PathVariable Long branchId) {
        return branches.findById(branchId)
                .orElseThrow(() -> new NotFoundException("Branch not found (id:" + branchId + ")"));
    }

    @RequestMapping(value = "/branches/{branchId}", method = {RequestMethod.PUT, RequestMethod.POST})
    public String branchEdit(@PathVariable Long branchId, @RequestBody Branch branchReq) {
        branches.findById(branchId).ifPresentOrElse(branch -> {
            branch.setName(branchReq.getName());
            branch.setManager(branchReq.getManager());
            branch.setMgrStartDate(branchReq.getMgrStartDate());
        }, () -> {
            branchReq.setId(branchId);
            branches.save(branchReq);
        });
        return "redirect:/api/branches/" + branchId;
    }

    @PostMapping("/branches/new")
    public Branch newBranchPost(@Valid @RequestBody Branch branch) {
        return branches.save(branch);
    }

    @DeleteMapping("/branches/{branchId}")
    public void deleteBranch(@PathVariable Long branchId) {
        branches.deleteById(branchId);
    }
}
