package io.github.kwisatzx.springmvccompany.model.supplier.dto;

import io.github.kwisatzx.springmvccompany.model.supplier.BranchSupplier;
import io.github.kwisatzx.springmvccompany.services.interfaces.SupplierService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SupplierModelMapper {

    protected SupplierService service;

    @Mapping(target = "branchId", expression = "java(supplier.getBranch().getId())")
    public abstract SupplierGetDto supplierToSupplierGetDto(BranchSupplier supplier);

    @Mapping(target = "branch", expression = "java(service.findBranchById(supplierInputDto.branchId()).get())")
    public abstract BranchSupplier supplierInputDtoToSupplier(SupplierInputDto supplierInputDto);

    @Autowired
    public void setService(SupplierService service) {
        this.service = service;
    }
}
