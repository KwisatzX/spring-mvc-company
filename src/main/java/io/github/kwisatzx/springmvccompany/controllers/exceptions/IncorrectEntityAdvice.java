package io.github.kwisatzx.springmvccompany.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IncorrectEntityAdvice {

    @ResponseBody
    @ExceptionHandler(IncorrectEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String incorrectEntityExceptionHandler(IncorrectEntityException ex) {
        return ex.getMessage();
    }
}
