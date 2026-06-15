package com.supriya.LMS.controller;

import com.supriya.LMS.dto.BookIssueDto;
import com.supriya.LMS.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<BookIssueDto>> issueBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Book Issued Successfully",
                        bookIssueService.issueBook(bookId, memberId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookIssueDto>>> getAllIssuedBooks() {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Issue Records Retrieved",
                        bookIssueService.getAllIssuedBooks()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookIssueDto>> getIssueById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Issue Record Found",
                        bookIssueService.getBookIssueById(id)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<BookIssueDto>>> getActiveIssues() {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Active Issues Retrieved",
                        bookIssueService.getActiveIssues()));
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<ApiResponse<BookIssueDto>> returnBook(@PathVariable Long issueId) {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Book Returned Successfully",
                        bookIssueService.returnBook(issueId)));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<List<BookIssueDto>>> getMemberIssuedBooks(@PathVariable Long memberId) {
        return ResponseEntity.ok(new ApiResponse<>(
                "200", "Member Issues Retrieved",
                bookIssueService.getMemberIssuedBooks(memberId)));
    }
}