package com.example.taskmanager.exception;

/**
 * Signals that a request contains invalid or unacceptable data.
 */
public class BadRequestException extends RuntimeException{

    /**
     * Creates an exception with a caller-provided explanation.
     *
     * @param message explanation of the invalid request
     */
    public BadRequestException(String message) {
        super(message);
    }
}
