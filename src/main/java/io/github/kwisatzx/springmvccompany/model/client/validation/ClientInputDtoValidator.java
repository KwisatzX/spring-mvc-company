package io.github.kwisatzx.springmvccompany.model.client.validation;

import io.github.kwisatzx.springmvccompany.model.client.dto.ClientInputDto;
import io.github.kwisatzx.springmvccompany.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class ClientInputDtoValidator implements Validator {

    private final ClientService service;

    @Override public boolean supports(Class<?> clazz) {
        return ClientInputDto.class.equals(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Field empty");
        if (!service.branchExistsById(((ClientInputDto) target).branchId()))
            errors.rejectValue("branchId", "", "Invalid branchId - not found");
    }
}
