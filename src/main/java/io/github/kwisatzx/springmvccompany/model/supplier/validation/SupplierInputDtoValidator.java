package io.github.kwisatzx.springmvccompany.model.supplier.validation;

import io.github.kwisatzx.springmvccompany.model.supplier.dto.SupplierInputDto;
import io.github.kwisatzx.springmvccompany.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class SupplierInputDtoValidator implements Validator {

    private final SupplierService service;

    @Override public boolean supports(Class<?> clazz) {
        return SupplierInputDto.class.equals(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        SupplierInputDto dto = (SupplierInputDto) target;
        if (service.findBranchById(dto.branchId()).isEmpty())
            errors.rejectValue("branchId", "", "Invalid branchId - not found");
        if (!StringUtils.hasText(dto.supplierName()))
            errors.rejectValue("supplierName", "", "supplierName is empty or null");
        if (!StringUtils.hasText(dto.supplierType()))
            errors.rejectValue("supplierType", "", "supplierType is empty or null");
    }
}
