package com.supriya.LMS.controller;

import com.supriya.LMS.dto.MemberDto;
import com.supriya.LMS.response.ApiResponse;
import com.supriya.LMS.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // Create Member
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MemberDto>> createMember(@Valid @RequestBody MemberDto dto)
    {
        MemberDto savedMember = memberService.createMember(dto);
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Member Created Successfully",
                        savedMember));
    }

    // Get All Members
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberDto>>> getAllMembers() {
        List<MemberDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Members Retrieved Successfully",
                        members));
    }

    // Get Member By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto>> getMemberById(@PathVariable Long id)
    {
        MemberDto member = memberService.getMemberById(id);
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Member Found",
                        member));
    }

    // Update Member
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto>> updateMember(
            @PathVariable Long id, @Valid @RequestBody MemberDto dto)
    {
        MemberDto updatedMember = memberService.updateMember(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Member Updated Successfully",
                        updatedMember));
    }

    // Delete Member
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMember(@PathVariable Long id)
    {
        memberService.deleteMember(id);
        return ResponseEntity.ok(new ApiResponse<>(
                        "200",
                        "Member Deleted Successfully",
                        null));
    }
}