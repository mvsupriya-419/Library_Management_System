package com.supriya.LMS.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDto {

    @NotBlank(message = "ISBN cannot be empty")
    private String isbn;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String author;

    private String category;

    @Min(value = 1, message = "Available copies must be greater than 0")
    private Integer availableCopies;

}