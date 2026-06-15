package com.supriya.LMS.service;

import com.supriya.LMS.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto dto);

    BookDto getBookById(Long id);

    List<BookDto> getAllBooks();

    BookDto updateBook(Long id, BookDto dto);

    void deleteBook(Long id);
}