package io.github.kwisatzx.springmvccompany.controllers.exceptions;

public class IncorrectEntityException extends RuntimeException {

    public IncorrectEntityException() {
        super("The sent entity has failed validation.");
    }
}
