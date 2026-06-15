package com.supriya.LMS.controller;

import com.supriya.LMS.Entity.BookIssue;
import com.supriya.LMS.service.BookIssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/book-issues")
public class BookIssueController {

    private final BookIssueService bookIssueService;

    public BookIssueController(BookIssueService bookIssueService) {
        this.bookIssueService = bookIssueService;
    }

    // Issue Book
    @PostMapping
    public ResponseEntity<BookIssue> issueBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId) {

        return ResponseEntity.ok(
                bookIssueService.issueBook(bookId, memberId));
    }

    // Get All Issue Records
    @GetMapping
    public ResponseEntity<List<BookIssue>> getAllIssuedBooks() {

        return ResponseEntity.ok(
                bookIssueService.getAllIssuedBooks());
    }

    // Get Issue By Id
    @GetMapping("/{id}")
    public ResponseEntity<BookIssue> getBookIssueById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookIssueService.getBookIssueById(id));
    }

    // Get Active Issues
    @GetMapping("/active")
    public ResponseEntity<List<BookIssue>> getActiveIssues() {

        return ResponseEntity.ok(
                bookIssueService.getActiveIssues());
    }

    // Return Book
    @PutMapping("/{issueId}")
    public ResponseEntity<BookIssue> returnBook(
            @PathVariable Long issueId) {

        return ResponseEntity.ok(
                bookIssueService.returnBook(issueId));
    }
}