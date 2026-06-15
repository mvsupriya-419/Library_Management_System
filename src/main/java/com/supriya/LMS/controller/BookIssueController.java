package com.supriya.LMS.controller;

import com.supriya.LMS.dto.BookIssueDto;
import com.supriya.LMS.service.BookIssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/book-issues")
public class BookIssueController {

    private final BookIssueService bookIssueService;

    public BookIssueController(
            BookIssueService bookIssueService) {
        this.bookIssueService = bookIssueService;
    }

    @PostMapping
    public ResponseEntity<BookIssueDto> issueBook(@RequestParam Long bookId, @RequestParam Long memberId)
    {
        return ResponseEntity.ok(bookIssueService.issueBook(bookId, memberId));
    }

    @GetMapping
    public ResponseEntity<List<BookIssueDto>>
    getAllIssuedBooks() {
        return ResponseEntity.ok(bookIssueService.getAllIssuedBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookIssueDto>
    getBookIssueById(@PathVariable Long id) {
        return ResponseEntity.ok(bookIssueService.getBookIssueById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<BookIssueDto>>
    getActiveIssues() {
        return ResponseEntity.ok(bookIssueService.getActiveIssues());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BookIssueDto>>
    getMemberIssuedBooks(@PathVariable Long memberId) {
        return ResponseEntity.ok(bookIssueService.getMemberIssuedBooks(memberId));
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<BookIssueDto>
    returnBook(@PathVariable Long issueId) {
        return ResponseEntity.ok(bookIssueService.returnBook(issueId));
    }
}