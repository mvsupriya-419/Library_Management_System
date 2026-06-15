package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Book;
import com.supriya.LMS.Entity.BookIssue;
import com.supriya.LMS.Entity.Member;
import com.supriya.LMS.dto.BookIssueDto;
import com.supriya.LMS.exception.*;
import com.supriya.LMS.repository.BookIssueRepository;
import com.supriya.LMS.repository.BookRepository;
import com.supriya.LMS.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookIssueServiceImpl implements BookIssueService {

    private final BookIssueRepository bookIssueRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BookIssueServiceImpl(
            BookIssueRepository bookIssueRepository,
            BookRepository bookRepository,
            MemberRepository memberRepository) {

        this.bookIssueRepository = bookIssueRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    private BookIssueDto mapToDto(BookIssue issue) {

        BookIssueDto dto = new BookIssueDto();

        dto.setId(issue.getId());
        dto.setBookId(issue.getBook().getId());
        dto.setMemberId(issue.getMember().getId());
        dto.setIssueDate(issue.getIssueDate());
        dto.setReturnDate(issue.getReturnDate());
        dto.setStatus(issue.getStatus());

        return dto;
    }

    @Override
    public BookIssueDto issueBook(
            Long bookId,
            Long memberId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book Not Found"));

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                        new MemberNotFoundException("Member Not Found"));

        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("Book Unavailable");
        }

        long count = bookIssueRepository.countByMemberIdAndStatus(memberId, "ISSUED");

        if (count >= 3) {
            throw new MaximumBookLimitExceededException("Cannot issue more than 3 books");
        }

        BookIssue issue = new BookIssue();

        issue.setBook(book);
        issue.setMember(member);
        issue.setIssueDate(LocalDate.now());
        issue.setStatus("ISSUED");

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        BookIssue savedIssue = bookIssueRepository.save(issue);

        return mapToDto(savedIssue);
    }

    @Override
    public BookIssueDto getBookIssueById(Long id) {

        BookIssue issue = bookIssueRepository.findById(id).orElseThrow(() ->
                new IssueRecordNotFoundException("Issue Not Found"));
        return mapToDto(issue);
    }

    @Override
    public List<BookIssueDto> getAllIssuedBooks() {

        return bookIssueRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookIssueDto> getActiveIssues() {

        return bookIssueRepository
                .findByStatus("ISSUED")
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookIssueDto> getMemberIssuedBooks(
            Long memberId) {

        return bookIssueRepository
                .findByMemberId(memberId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookIssueDto returnBook(Long issueId) {

        BookIssue issue = bookIssueRepository.findById(issueId).orElseThrow(() ->
                new IssueRecordNotFoundException("Issue Not Found"));

        if ("RETURNED".equals(issue.getStatus())) {
            throw new RuntimeException("Book Already Returned");
        }

        issue.setStatus("RETURNED");
        issue.setReturnDate(LocalDate.now());
        Book book = issue.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        return mapToDto(bookIssueRepository.save(issue));
    }
}