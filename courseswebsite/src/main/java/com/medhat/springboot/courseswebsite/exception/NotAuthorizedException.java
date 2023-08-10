package com.medhat.springboot.courseswebsite.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
