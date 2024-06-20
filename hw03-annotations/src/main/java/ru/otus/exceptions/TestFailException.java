package ru.otus.exceptions;

public class TestFailException extends RuntimeException {
    public TestFailException() {
        super();
    }

    public TestFailException(String message) {
        super(message);
    }

    public TestFailException(Throwable cause) {
        super(cause);
    }

    public TestFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
