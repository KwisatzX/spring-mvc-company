package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.DuplicateException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.InvalidEntityException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchGetDto;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchInputDto;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchModelMapper;
import io.github.kwisatzx.springmvccompany.model.branch.validation.BranchInputDtoValidator;
import io.github.kwisatzx.springmvccompany.services.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BranchesRestController {

    private final BranchService service;
    private final BranchModelMapper mapper;
    private final BranchInputDtoValidator validator;

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setValidator(validator);
    }

    @GetMapping("/branches")
    public ResponseEntity<Collection<BranchGetDto>> getAllBranches() {
        return ResponseEntity.ok(service.getAllBranches().stream().map(mapper::branchToBranchGetDto).toList());
    }

    @GetMapping("/branches/{id}")
    public ResponseEntity<BranchGetDto> getBranch(@PathVariable Long id) {
        Optional<Branch> result = service.findById(id);
        if (result.isPresent()) return ResponseEntity.ok(mapper.branchToBranchGetDto(result.get()));
        else throw new NotFoundException();
    }

    @Transactional
    @PutMapping(value = "/branches/{id}")
    public ResponseEntity<BranchGetDto> branchEdit(@PathVariable Long id,
                                                   @RequestBody @Valid BranchInputDto body,
                                                   BindingResult result) throws InvalidEntityException {
        if (result.hasErrors()) throw new InvalidEntityException(result);
        Optional<Branch> byId = service.findById(id);
        Branch requestBranch = mapper.branchInputDtoToBranch(body);
        if (byId.isPresent()) {
            Branch dbBranch = byId.get();
            dbBranch.setName(requestBranch.getName());
            dbBranch.setManager(requestBranch.getManager());
            dbBranch.setMgrStartDate(requestBranch.getMgrStartDate());
            return ResponseEntity.noContent().location(URI.create("/branches/" + id)).build();
        } else {
            requestBranch.setId(id);
            Branch savedBranch = service.save(requestBranch);
            return ResponseEntity.created(URI.create("/branches/" + savedBranch.getId()))
                    .body(mapper.branchToBranchGetDto(savedBranch));
        }
    }

    @PostMapping("/branches")
    public ResponseEntity<BranchGetDto> newBranchPost(@RequestBody @Valid BranchInputDto body, BindingResult result)
            throws InvalidEntityException {
        if (result.hasErrors()) throw new InvalidEntityException(result);
        if (service.existsByName(body.name()))
            throw new DuplicateException("Branch with name '" + body.name() + "' already exists");
        else {
            Branch savedBranch = service.save(mapper.branchInputDtoToBranch(body));
            return ResponseEntity.created(URI.create("/branches/" + savedBranch.getId()))
                    .body(mapper.branchToBranchGetDto(savedBranch));
        }
    }

    @DeleteMapping("/branches/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
