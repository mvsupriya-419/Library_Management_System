package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Member;
import com.supriya.LMS.Entity.Providers;
import com.supriya.LMS.Entity.Users;
import com.supriya.LMS.dto.MemberDto;
import com.supriya.LMS.exception.DuplicateMemberCodeException;
import com.supriya.LMS.exception.MemberNotFoundException;
import com.supriya.LMS.mapper.MemberMapper;
import com.supriya.LMS.repository.MemberRepository;
import com.supriya.LMS.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public MemberDto createMember(MemberDto dto) {
        if (memberRepository.existsByMemberCode(dto.getMemberCode())) {
            throw new DuplicateMemberCodeException("Member Code already exists");
        }

        String getPass = generatePassword();

        Users users = Users.builder()
                .email(dto.getEmail())
                .role(Providers.USER)
                .password(passwordEncoder.encode(getPass))
                .build();

        usersRepository.save(users);

        Member member = memberMapper.toEntity(dto);
        MemberDto result = memberMapper.toDto(memberRepository.save(member));
        result.setGeneratedPassword(getPass);   // ← carry the plain-text password out
        return result;
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

    public String generatePassword()
    {
        return "Zkteco@123";
    }
}