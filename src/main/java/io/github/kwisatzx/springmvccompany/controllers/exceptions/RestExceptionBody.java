package io.github.kwisatzx.springmvccompany.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class RestExceptionBody {
    private final Date timestamp;
    private final String errorCode;
    private final List<Object> errors;

    public RestExceptionBody(String errorCode) {
        timestamp = new Date();
        this.errorCode = errorCode;
        errors = new ArrayList<>();
    }

    public RestExceptionBody(String errorCode, BindingResult result) {
        this(errorCode);
        result.getFieldErrors().forEach(fieldError -> errors.add(FieldErrorDetails.fromFieldError(fieldError)));
    }

    public RestExceptionBody(String errorCode, String errorMessage) {
        this(errorCode);
        errors.add(errorMessage);
    }

    @AllArgsConstructor
    @Getter
    static public class FieldErrorDetails {
        private final String field;
        private final String value;
        private final String errorMessage;

        static public FieldErrorDetails fromFieldError(FieldError error) {
            String rejectedValue = error.getRejectedValue() == null ? "null" : error.getRejectedValue().toString();
            return new FieldErrorDetails(error.getField(), rejectedValue, error.getDefaultMessage());
        }
    }
}
