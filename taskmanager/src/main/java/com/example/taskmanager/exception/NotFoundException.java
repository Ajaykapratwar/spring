package com.example.taskmanager.exception;

/**
 * Signals that a requested application resource could not be found.
 */
public class NotFoundException extends RuntimeException{

    /**
     * Creates an exception with a caller-provided explanation.
     *
     * @param message explanation of the missing resource
     */
    public NotFoundException(String message) {
        super(message);
    }
}
