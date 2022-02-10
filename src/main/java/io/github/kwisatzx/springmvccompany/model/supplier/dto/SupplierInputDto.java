package io.github.kwisatzx.springmvccompany.model.supplier.dto;

public record SupplierInputDto(
        Long branchId,
        String supplierName,
        String supplierType
) {}
