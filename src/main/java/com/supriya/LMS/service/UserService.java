package com.supriya.LMS.service;

import com.supriya.LMS.dto.ChangePasswordDto;
import com.supriya.LMS.dto.RegisterDto;
import com.supriya.LMS.dto.ResetPasswordDto;
import com.supriya.LMS.Entity.Users;

public interface UserService {

    Users register(RegisterDto dto);

    String verifyEmail(String token);

    void resendVerificationToken(String email);

    void forgotPassword(String email);

    void resetPassword(ResetPasswordDto dto);

    void changePassword(ChangePasswordDto changePasswordDto);
}