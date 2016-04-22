package com.redalpha.test.service;
/**
 * 
 * @author Dzmitry_Brashavets
 *  Service Exception
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public ServiceException(final String message) {
        super(message);
    }

    /**
     * Constructs a new service exception with the specified detailed message and cause.
     *
     * @param message the detailed message of the issue
     * @param cause original cause of exception
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceException(final Throwable cause) {
        super(cause);
    }
}
