package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Book;
import com.supriya.LMS.dto.BookDto;
import com.supriya.LMS.exception.BookNotFoundException;
import com.supriya.LMS.exception.DuplicateIsbnException;
import com.supriya.LMS.mapper.BookMapper;
import com.supriya.LMS.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public BookDto createBook(BookDto dto) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new DuplicateIsbnException("ISBN already exists");
        }
        Book book = bookMapper.toEntity(dto);
        book.setId(null);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
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
        return bookMapper.toDto(bookRepository.save(existing));
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));
        bookRepository.delete(book);
    }
}