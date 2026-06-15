package com.supriya.LMS.exception;

import com.supriya.LMS.response.ApiResponse;
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

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<String>>
    handleBookNotFound(BookNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>("404", ex.getMessage(), null));
    }

}

