package com.redalpha.test.service;

public class EntityValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityValidationException(final String message) {
        super(message);
    }

    /**
     * Constructs a new entity validation exception with the specified detailed message and cause.
     *
     * @param message the detailed message of the issue
     * @param cause original cause of exception
     */
    public EntityValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EntityValidationException(final Throwable cause) {
        super(cause);
    }

}
