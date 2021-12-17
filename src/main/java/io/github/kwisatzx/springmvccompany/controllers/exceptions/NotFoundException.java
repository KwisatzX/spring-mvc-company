package io.github.kwisatzx.springmvccompany.controllers.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
