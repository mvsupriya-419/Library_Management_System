package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Book;
import com.supriya.LMS.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);

    }

    @Override
    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("ID IS NOT PRESENT"));
        return book;
    }

    @Override
    public List<Book> getAllBooks() {

        return  bookRepository.findAll();
    }
}