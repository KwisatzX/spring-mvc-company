package io.github.kwisatzx.springmvccompany.model.client.validation;

import io.github.kwisatzx.springmvccompany.model.client.dto.ClientInputDto;
import io.github.kwisatzx.springmvccompany.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class ClientInputDtoValidator implements Validator {

    private final ClientService service;

    @Override public boolean supports(Class<?> clazz) {
        return ClientInputDto.class.equals(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        ClientInputDto dto = (ClientInputDto) target;
        if (!StringUtils.hasText(dto.name()))
            errors.rejectValue("name", "", "Field 'name' is empty or null");
        if (!service.branchExistsById(dto.branchId()))
            errors.rejectValue("branchId", "", "Invalid branchId - not found");

    }
}
