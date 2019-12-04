package ru.itpark.framework.exception;

public class ComponentException extends RuntimeException {
    public ComponentException() {
    }

    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentException(Throwable cause) {
        super(cause);
    }

    public ComponentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
