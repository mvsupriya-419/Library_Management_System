package com.supriya.LMS.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookIssueDto {

    private Long id;

    private Long bookId;

    private Long memberId;

    private LocalDate issueDate;

    private LocalDate returnDate;

    private String status;
}