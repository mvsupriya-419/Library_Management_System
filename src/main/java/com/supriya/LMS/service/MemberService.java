
package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Member;

import java.util.List;

public interface MemberService {

    Member createMember(Member member);

    List<Member> getAllMembers();

    Member getMemberById(Long id);
}