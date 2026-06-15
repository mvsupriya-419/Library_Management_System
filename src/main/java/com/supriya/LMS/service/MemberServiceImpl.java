package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Member;
import com.supriya.LMS.exception.MemberNotFoundException;
import com.supriya.LMS.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(Member member) {
        if(memberRepository.existsByMemberCode(member.getMemberCode())){
            throw new RuntimeException("Member code already exists");
        }
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }

    @Override
    public Member updateMember(Long id, Member member) {
        Member existing = memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException("Member Not Found"));
        existing.setName(member.getName());
        existing.setEmail(member.getEmail());
        existing.setPhoneNumber(member.getPhoneNumber());
        return memberRepository.save(existing);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException("Member Not Found"));
        memberRepository.delete(member);
    }
}