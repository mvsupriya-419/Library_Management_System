package com.supriya.LMS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDto {

    private String memberCode;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Enter a valid email")
    private String email;

    private String phoneNumber;

    private String generatedPassword;
}