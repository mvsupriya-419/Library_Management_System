package com.supriya.LMS.controller;

import com.supriya.LMS.Entity.BookIssue;
import com.supriya.LMS.service.BookIssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/book-issues")
public class BookIssueController {

    private final BookIssueService bookIssueService;

    public BookIssueController(BookIssueService bookIssueService) {
        this.bookIssueService = bookIssueService;
    }

    @PostMapping
    public ResponseEntity<BookIssue> issueBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId) {

        return ResponseEntity.ok(
                bookIssueService.issueBook(bookId, memberId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookIssue> getBookIssueById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookIssueService.getBookIssueById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookIssue>> getAllIssuedBooks() {

        return ResponseEntity.ok(
                bookIssueService.getAllIssuedBooks());
    }
}