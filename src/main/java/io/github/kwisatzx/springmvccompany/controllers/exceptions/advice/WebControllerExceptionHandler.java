package io.github.kwisatzx.springmvccompany.controllers.exceptions.advice;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Priority;

@ControllerAdvice("io.github.kwisatzx.springmvccompany.controllers.web")
@Priority(1)
public class WebControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundExceptionHandler(NotFoundException ex, WebRequest request) {
        return "Resource not found: " + request.getDescription(false);
    }
}
