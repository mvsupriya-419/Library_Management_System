package com.supriya.LMS.exception;

public class BookUnavailableException
        extends RuntimeException {

    public BookUnavailableException(String message) {
        super(message);
    }
}
