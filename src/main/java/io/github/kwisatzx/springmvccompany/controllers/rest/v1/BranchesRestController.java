package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchGetDto;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchInputDto;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchModelMapper;
import io.github.kwisatzx.springmvccompany.services.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BranchesRestController {

    private final BranchService service;
    private final BranchModelMapper mapper;

    @GetMapping("/branches")
    public List<BranchGetDto> listBranches() {
        return service.getAllBranches().stream().map(mapper::branchToBranchGetDto).toList();
    }

    @GetMapping("/branches/{id}")
    public ResponseEntity<BranchGetDto> branchDetails(@PathVariable Long id) {
        Optional<Branch> result = service.findById(id);
        if (result.isPresent()) return ResponseEntity.ok(mapper.branchToBranchGetDto(result.get()));
        else throw new NotFoundException();
    }

    @Transactional
    @PutMapping(value = "/branches/{id}")
    public ResponseEntity<Void> branchEdit(@PathVariable Long id, @RequestBody BranchInputDto body) { //TODO Valid
        Optional<Branch> result = service.findById(id);
        Branch newBranch = mapper.branchInputDtoToBranch(body);
        if (result.isPresent()) { //TODO Save on id
            Branch dbBranch = result.get();
            dbBranch.setName(newBranch.getName());
            dbBranch.setManager(newBranch.getManager());
            dbBranch.setMgrStartDate(newBranch.getMgrStartDate());
            return ResponseEntity.noContent().location(URI.create("/branches/" + id)).build();
        } else {
            newBranch.setId(id);
            Branch savedBranch = service.save(newBranch);
            return ResponseEntity.created(URI.create("/branches/" + savedBranch.getId())).build();
        }
    }

    @PostMapping("/branches/new")
    public ResponseEntity<Object> newBranchPost(@RequestBody BranchInputDto body) { //TODO Valid
//        if (result.hasErrors()) return ResponseEntity.status(422).build(); //+errors in json?
        if (service.existsByName(body.name()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Branch with that name already exists: " + body.name());
        else {
            Branch savedBranch = service.save(mapper.branchInputDtoToBranch(body));
            return ResponseEntity.created(URI.create("/branches/" + savedBranch.getId())).build();
        }
    }

    @DeleteMapping("/branches/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
