package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Book;
import com.supriya.LMS.dto.BookDto;
import com.supriya.LMS.exception.BookNotFoundException;
import com.supriya.LMS.exception.DuplicateIsbnException;
import com.supriya.LMS.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private BookDto mapToDto(Book book) {

        BookDto dto = new BookDto();

        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setCategory(book.getCategory());
        dto.setAvailableCopies(book.getAvailableCopies());

        return dto;
    }

    private Book mapToEntity(BookDto dto) {

        Book book = new Book();

        book.setId(dto.getId());
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setAvailableCopies(dto.getAvailableCopies());

        return book;
    }

    @Override
    public BookDto createBook(BookDto dto) {

        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new DuplicateIsbnException(
                    "ISBN already exists");
        }

        Book book = new Book();

        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setAvailableCopies(dto.getAvailableCopies());

        Book savedBook = bookRepository.save(book);

        return mapToDto(savedBook);
    }

    @Override
    public BookDto getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));

        return mapToDto(book);
    }

    @Override
    public List<BookDto> getAllBooks() {

        return bookRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(Long id, BookDto dto) {

        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));

        existing.setIsbn(dto.getIsbn());
        existing.setTitle(dto.getTitle());
        existing.setAuthor(dto.getAuthor());
        existing.setCategory(dto.getCategory());
        existing.setAvailableCopies(dto.getAvailableCopies());

        Book updatedBook = bookRepository.save(existing);

        return mapToDto(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));

        bookRepository.delete(book);
    }
}