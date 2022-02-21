package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.DuplicateException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.IncorrectEntityException;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplierId;
import io.github.kwisatzx.springmvccompany.model.supplier.dto.SupplierGetDto;
import io.github.kwisatzx.springmvccompany.model.supplier.dto.SupplierInputDto;
import io.github.kwisatzx.springmvccompany.model.supplier.dto.SupplierModelMapper;
import io.github.kwisatzx.springmvccompany.model.supplier.validation.SupplierInputDtoValidator;
import io.github.kwisatzx.springmvccompany.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SuppliersRestController {

    private final SupplierService service;
    private final SupplierModelMapper mapper;
    private final SupplierInputDtoValidator validator;

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setValidator(validator);
    }

    @GetMapping("/suppliers")
    public ResponseEntity<Collection<SupplierGetDto>> getAllSuppliers() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::supplierToSupplierGetDto).toList());
    }

    @GetMapping("/branches/{id}/suppliers")
    public ResponseEntity<Collection<SupplierGetDto>> getSuppliersForBranch(@PathVariable Long id) {
        return ResponseEntity.ok(
                service.findAllForBranchId(id).stream().map(mapper::supplierToSupplierGetDto).toList());
    }

    @PostMapping("/suppliers")
    public ResponseEntity<Void> createSupplier(@RequestBody @Valid SupplierInputDto body, BindingResult result) {
        if (result.hasErrors()) throw new IncorrectEntityException();
        BranchSupplier newSupplier = mapper.supplierInputDtoToSupplier(body);
        if (service.existsById(new BranchSupplierId(newSupplier.getBranch(), newSupplier.getSupplierName()))) {
            throw new DuplicateException();
        } else {
            BranchSupplier saved = service.save(newSupplier);
            return ResponseEntity.noContent().location(
                    URI.create("/branches/" + saved.getBranch().getId() + "/suppliers")).build();
        }
    }
}
