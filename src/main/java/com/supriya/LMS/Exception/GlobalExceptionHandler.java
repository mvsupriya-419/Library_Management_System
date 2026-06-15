package com.supriya.LMS.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(LocalDate.now());
        errorResponse.setStatus(ex.getStatusCode().value());
        errorResponse.setMessage("Fields are empty.");
        errorResponse.setError(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
        errorResponse.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}

