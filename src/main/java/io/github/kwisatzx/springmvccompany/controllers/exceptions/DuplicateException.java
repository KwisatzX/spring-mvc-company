package io.github.kwisatzx.springmvccompany.controllers.exceptions;

public class DuplicateException extends RuntimeException {
    static public final String ERROR_CODE = "DUPLICATE_ENTRY";

    public DuplicateException(String message) {
        super(message);
    }
}
