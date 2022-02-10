package io.github.kwisatzx.springmvccompany.model.supplier.dto;

public record SupplierGetDto(
        Long branchId,
        String supplierName,
        String supplierType
) {}
