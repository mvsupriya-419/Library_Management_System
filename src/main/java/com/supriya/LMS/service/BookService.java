package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Book;

import java.util.List;

public interface BookService {

    Book createBook(Book book);


    Book getBookById(Long id);

    List<Book> getAllBooks();

    Book updateBook(Long id,Book book);

    void deleteBook(Long id);


}