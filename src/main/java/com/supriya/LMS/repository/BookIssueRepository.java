package com.supriya.LMS.repository;
import com.supriya.LMS.Entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookIssueRepository
        extends JpaRepository<BookIssue, Long> {

    List<BookIssue> findByStatus(String status);

    long countByMemberIdAndStatus(Long memberId, String status);

    List<BookIssue> findByMemberId(Long memberId);
}