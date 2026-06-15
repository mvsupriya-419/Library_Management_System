package com.supriya.LMS.repository;

import com.supriya.LMS.Entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
}