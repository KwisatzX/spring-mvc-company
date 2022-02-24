package io.github.kwisatzx.springmvccompany.controllers.exceptions;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class InvalidEntityException extends BindException {
    static public final String ERROR_CODE = "INVALID_ENTITY";

    public InvalidEntityException(BindingResult result) {
        super(result);
    }
}
