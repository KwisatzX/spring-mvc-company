package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.DuplicateException;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplierId;
import io.github.kwisatzx.springmvccompany.model.supplier.dto.SupplierGetDto;
import io.github.kwisatzx.springmvccompany.model.supplier.dto.SupplierInputDto;
import io.github.kwisatzx.springmvccompany.model.supplier.dto.SupplierModelMapper;
import io.github.kwisatzx.springmvccompany.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SuppliersRestController {

    private final SupplierService service;
    private final SupplierModelMapper mapper;

    @GetMapping("/suppliers")
    public ResponseEntity<Collection<SupplierGetDto>> getAllSuppliers() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::supplierToSupplierGetDto).toList());
    }

    @GetMapping("/suppliers")
    public ResponseEntity<SupplierGetDto> getSupplierByName(@RequestParam("name") String name) {
        Optional<BranchSupplier> result = service.findByName(name);
        if (result.isPresent()) return ResponseEntity.ok(mapper.supplierToSupplierGetDto(result.get()));
        else return ResponseEntity.ok().build();
    }

    @GetMapping("/branches/{id}/suppliers")
    public ResponseEntity<Collection<SupplierGetDto>> getSuppliersForBranch(@PathVariable Long id) {
        return ResponseEntity.ok(
                service.findAllForBranchId(id).stream().map(mapper::supplierToSupplierGetDto).toList());
    }

    @PostMapping("/suppliers/new")
    public ResponseEntity<Void> createSupplier(@RequestBody SupplierInputDto body) {
        BranchSupplier newSupplier = mapper.supplierInputDtoToSupplier(body);
        if (service.existsById(new BranchSupplierId(newSupplier.getBranch(), newSupplier.getSupplierName()))) {
            throw new DuplicateException();
        } else {
            BranchSupplier saved = service.save(newSupplier);
            return ResponseEntity.created(URI.create("/suppliers?name=" + saved.getSupplierName())).build();
        }
    }
}
