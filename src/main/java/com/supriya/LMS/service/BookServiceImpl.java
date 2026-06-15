package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Book;
import com.supriya.LMS.exception.BookNotFoundException;
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
        if(bookRepository.existsByIsbn(book.getIsbn())){
            throw new RuntimeException("ISBN already exists");
        }
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

    @Override
    public Book updateBook(Long id, Book book) {

        Book existing = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book Not Found"));
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setCategory(book.getCategory());
        return bookRepository.save(existing);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book Not Found"));
        bookRepository.delete(book);
    }


}