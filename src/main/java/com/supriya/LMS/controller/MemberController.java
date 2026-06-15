package com.supriya.LMS.controller;

import com.supriya.LMS.dto.MemberDto;
import com.supriya.LMS.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService)
    {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@Valid @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.createMember(dto));
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers()
    {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long id)
    {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto>
    updateMember(@PathVariable Long id, @Valid @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id)
    {
        memberService.deleteMember(id);
        return ResponseEntity.ok("Member deleted successfully");
    }
}