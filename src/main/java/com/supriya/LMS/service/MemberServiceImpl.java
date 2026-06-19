package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Member;
import com.supriya.LMS.dto.MemberDto;
import com.supriya.LMS.exception.DuplicateMemberCodeException;
import com.supriya.LMS.exception.MemberNotFoundException;
import com.supriya.LMS.mapper.MemberMapper;
import com.supriya.LMS.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }


    @Override
    public MemberDto createMember(MemberDto dto) {
        if (memberRepository.existsByMemberCode(dto.getMemberCode())) {
            throw new DuplicateMemberCodeException("Member Code already exists");
        }
        Member member = memberMapper.toEntity(dto);
        member.setId(null);
        return memberMapper.toDto(memberRepository.save(member));
    }

    @Override
    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member Not Found"));
        return memberMapper.toDto(member);
    }

    @Override
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(memberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto updateMember(Long id, MemberDto dto) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member Not Found"));
        existing.setMemberCode(dto.getMemberCode());
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhoneNumber(dto.getPhoneNumber());
        return memberMapper.toDto(memberRepository.save(existing));
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member Not Found"));
        memberRepository.delete(member);
    }
}