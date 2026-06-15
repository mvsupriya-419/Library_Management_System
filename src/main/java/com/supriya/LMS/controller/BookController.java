package com.supriya.LMS.controller;

import com.supriya.LMS.dto.BookDto;
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

    // Create Book
    @PostMapping
    public ResponseEntity<BookDto> createBook(
            @Valid @RequestBody BookDto dto) {

        return ResponseEntity.ok(
                bookService.createBook(dto));
    }

    // Get All Books
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {

        return ResponseEntity.ok(
                bookService.getAllBooks());
    }

    // Get Book By Id
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookService.getBookById(id));
    }

    // Update Book
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDto dto) {

        return ResponseEntity.ok(
                bookService.updateBook(id, dto));
    }

    // Delete Book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.ok(
                "Book deleted successfully");
    }
}