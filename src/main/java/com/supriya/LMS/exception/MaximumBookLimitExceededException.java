package com.supriya.LMS.exception;

public class MaximumBookLimitExceededException extends RuntimeException {

    public MaximumBookLimitExceededException(String message) {

        super(message);
    }
}
