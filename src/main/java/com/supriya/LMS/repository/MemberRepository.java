package com.supriya.LMS.repository;

import com.supriya.LMS.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByMemberCode(String memberCode);
}