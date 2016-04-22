package com.redalpha.test.repository.call;

public class CallRepositoryException extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    public CallRepositoryException() {
    }

    public CallRepositoryException(String message) {
        super(message);
    }

    public CallRepositoryException(Throwable cause) {
        super(cause);
    }

    public CallRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}