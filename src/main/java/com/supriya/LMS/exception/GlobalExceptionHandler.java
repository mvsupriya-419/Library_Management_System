package com.supriya.LMS.exception;

import com.supriya.LMS.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<String>>
    handleBookNotFound(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                        "404",
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiResponse<String>>
    handleMemberNotFound(MemberNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                        "404",
                        ex.getMessage(),
                        null));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        "400",
                        "Validation Failed",
                        errors));
    }

    @ExceptionHandler(IssueRecordNotFoundException.class)
    public ResponseEntity<ApiResponse<String>>
    handleIssueNotFound(IssueRecordNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                        "404",
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>>
    handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                        "400",
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<ApiResponse<String>>
    handleDuplicateIsbn(DuplicateIsbnException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(
                                "400",
                                ex.getMessage(),
                                null));
    }
    @ExceptionHandler(DuplicateMemberCodeException.class)
    public ResponseEntity<ApiResponse<String>>
    handleDuplicateMemberCode(DuplicateMemberCodeException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(
                                "400",
                                ex.getMessage(),
                                null));
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<ApiResponse<String>> handleBookUnavailable(BookUnavailableException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(
                                "400",
                                ex.getMessage(),
                                null));
    }

    @ExceptionHandler(MaximumBookLimitExceededException.class)
    public ResponseEntity<ApiResponse<String>>
    handleMaximumBookLimitExceeded(MaximumBookLimitExceededException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(
                                "400",
                                ex.getMessage(),
                                null));
    }

}