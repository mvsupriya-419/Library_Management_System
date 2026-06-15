package com.supriya.LMS.service;

import com.supriya.LMS.dto.MemberDto;

import java.util.List;

public interface MemberService {

    MemberDto createMember(MemberDto dto);

    MemberDto getMemberById(Long id);

    List<MemberDto> getAllMembers();

    MemberDto updateMember(Long id, MemberDto dto);

    void deleteMember(Long id);
}