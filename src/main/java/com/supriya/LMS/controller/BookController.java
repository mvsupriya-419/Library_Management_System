package com.supriya.LMS.controller;

import com.supriya.LMS.dto.BookDto;
import com.supriya.LMS.response.ApiResponse;
import com.supriya.LMS.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> createBook(@Valid @RequestBody BookDto dto) {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Book Created Successfully",
                        bookService.createBook(dto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks() {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Books Retrieved Successfully",
                        bookService.getAllBooks()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Book Found",
                        bookService.getBookById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto dto) {
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Book Updated Successfully",
                        bookService.updateBook(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Book Deleted Successfully",
                        null));
    }
}