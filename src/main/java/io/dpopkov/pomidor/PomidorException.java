package io.dpopkov.pomidor;

public class PomidorException extends RuntimeException {

    public PomidorException(String message) {
        super(message);
    }

    public PomidorException(String message, Throwable cause) {
        super(message, cause);
    }
}
