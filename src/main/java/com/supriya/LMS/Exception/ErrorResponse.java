package com.supriya.LMS.Exception;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ErrorResponse {

    private LocalDate timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
