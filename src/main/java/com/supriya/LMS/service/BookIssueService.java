package com.supriya.LMS.service;

import com.supriya.LMS.Entity.BookIssue;

import java.util.List;

public interface BookIssueService {

    BookIssue issueBook(Long bookId, Long memberId);

    BookIssue getBookIssueById(Long id);

    List<BookIssue> getAllIssuedBooks();

    List<BookIssue> getActiveIssues();


    BookIssue returnBook(Long issueId);
}