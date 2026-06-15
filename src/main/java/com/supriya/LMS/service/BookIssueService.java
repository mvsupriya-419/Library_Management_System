package com.supriya.LMS.service;

import com.supriya.LMS.dto.BookIssueDto;

import java.util.List;

public interface BookIssueService {

    BookIssueDto issueBook(Long bookId, Long memberId);

    BookIssueDto getBookIssueById(Long id);

    List<BookIssueDto> getAllIssuedBooks();

    List<BookIssueDto> getActiveIssues();

    List<BookIssueDto> getMemberIssuedBooks(Long memberId);

    BookIssueDto returnBook(Long issueId);
}