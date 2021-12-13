package io.github.kwisatzx.springmvccompany.model;

import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@Component
public class BranchFormatter implements Formatter<Branch> {

    private final BranchRepository branches;

    public BranchFormatter(BranchRepository branches) {
        this.branches = branches;
    }

    @Override
    public String print(Branch branch, Locale locale) {
        return branch.toString();
    }

    @Override
    public Branch parse(String branchToString, Locale locale) throws ParseException {
        List<Branch> branches = this.branches.getAllBranches();
        for (Branch branch : branches) {
            if (branch.toString().equals(branchToString)) return branch;
        }
        throw new ParseException("branch not found: " + branchToString, 0);
    }
}
