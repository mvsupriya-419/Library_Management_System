package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Book;
import com.supriya.LMS.Entity.BookIssue;
import com.supriya.LMS.Entity.Member;
import com.supriya.LMS.repository.BookIssueRepository;
import com.supriya.LMS.repository.BookRepository;
import com.supriya.LMS.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookIssueServiceImpl implements BookIssueService {

    private final BookIssueRepository bookIssueRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BookIssueServiceImpl(BookIssueRepository bookIssueRepository,
                                BookRepository bookRepository,
                                MemberRepository memberRepository) {
        this.bookIssueRepository = bookIssueRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public BookIssue issueBook(Long bookId, Long memberId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BookIssue issue = new BookIssue();

        issue.setBook(book);
        issue.setMember(member);
        issue.setIssueDate(LocalDate.now());
        issue.setStatus("ISSUED");

        return bookIssueRepository.save(issue);
    }

    @Override
    public BookIssue getBookIssueById(Long id) {

        return bookIssueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Issue record not found"));
    }

    @Override
    public List<BookIssue> getAllIssuedBooks() {
        return bookIssueRepository.findAll();
    }
}