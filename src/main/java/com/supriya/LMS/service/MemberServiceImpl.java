package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Member;
import com.supriya.LMS.dto.MemberDto;
import com.supriya.LMS.exception.DuplicateMemberCodeException;
import com.supriya.LMS.exception.MemberNotFoundException;
import com.supriya.LMS.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository)
    {
        this.memberRepository = memberRepository;
    }

    private MemberDto mapToDto(Member member) {

        MemberDto dto = new MemberDto();

        dto.setId(member.getId());
        dto.setMemberCode(member.getMemberCode());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPhoneNumber(member.getPhoneNumber());

        return dto;
    }

    private Member mapToEntity(MemberDto dto) {

        Member member = new Member();

        member.setId(dto.getId());
        member.setMemberCode(dto.getMemberCode());
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPhoneNumber(dto.getPhoneNumber());

        return member;
    }

    @Override
    public MemberDto createMember(MemberDto dto) {
        if (memberRepository.existsByMemberCode(dto.getMemberCode())) {
            throw new DuplicateMemberCodeException("Member Code already exists");
        }
        Member member = new Member();
        member.setMemberCode(dto.getMemberCode());
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPhoneNumber(dto.getPhoneNumber());
        Member savedMember = memberRepository.save(member);
        return mapToDto(savedMember);
    }

    @Override
    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException("Member Not Found"));
        return mapToDto(member);
    }

    @Override
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto updateMember(Long id, MemberDto dto) {
        Member existing = memberRepository.findById(id).orElseThrow(() ->
                        new MemberNotFoundException("Member Not Found"));
        existing.setMemberCode(dto.getMemberCode());
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhoneNumber(dto.getPhoneNumber());
        Member updatedMember = memberRepository.save(existing);
        return mapToDto(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException("Member Not Found"));
        memberRepository.delete(member);
    }
}