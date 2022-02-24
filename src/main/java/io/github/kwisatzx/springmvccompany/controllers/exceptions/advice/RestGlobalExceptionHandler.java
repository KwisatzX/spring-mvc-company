package io.github.kwisatzx.springmvccompany.controllers.exceptions.advice;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.DuplicateException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.InvalidEntityException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.RestExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestExceptionBody> notFoundExceptionHandler(NotFoundException ex, WebRequest request) {
        String errorMessage = "Resource not found: " + request.getDescription(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RestExceptionBody(NotFoundException.ERROR_CODE, errorMessage));
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<RestExceptionBody> incorrectEntityExceptionHandler(InvalidEntityException ex,
                                                                             WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new RestExceptionBody(InvalidEntityException.ERROR_CODE, ex.getBindingResult()));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<RestExceptionBody> duplicateExceptionHandler(DuplicateException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new RestExceptionBody(DuplicateException.ERROR_CODE, ex.getMessage()));
    }
}
