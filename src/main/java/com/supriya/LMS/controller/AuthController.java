package com.supriya.LMS.controller;

import com.supriya.LMS.Entity.Users;
import com.supriya.LMS.dto.*;
import com.supriya.LMS.event.RegistrationCompleteEvent;
import com.supriya.LMS.service.CustomUserDetailsService;
import com.supriya.LMS.service.JwtService;
import com.supriya.LMS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    // ── 1. REGISTER ──────────────────────────────────────────
    @PostMapping("/register")
    public String register(@RequestBody RegisterDto dto) {
        Users user = userService.register(dto);
        eventPublisher.publishEvent(
                new RegistrationCompleteEvent(user));
        return "Registration successful! "
                + "Check your email to verify your account.";
    }

    // ── 2. LOGIN ─────────────────────────────────────────────
    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(), requestDTO.getPassword()));

        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(requestDTO.getEmail());
        return jwtService.generateToken(String.valueOf(userDetails));
    }

    // ── 3. VERIFY EMAIL ──────────────────────────────────────
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {
        return userService.verifyEmail(token);
    }

    // ── 4. RESEND VERIFICATION TOKEN ─────────────────────────
    @PostMapping("/resend-token")
    public String resendToken(@RequestParam("email") String email) {
        userService.resendVerificationToken(email);
        return "A new verification email has been sent.";
    }

    // ── 5. FORGOT PASSWORD ───────────────────────────────────
    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestBody ForgotPasswordDto dto) {
        userService.forgotPassword(dto.getEmail());
        return "Password reset email sent.";
    }

    // ── 6. RESET PASSWORD ────────────────────────────────────
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordDto dto) {
        userService.resetPassword(dto);
        return "Password has been reset successfully.";
    }

    // ── 7. CHANGE PASSWORD (logged-in user) ──────────────────
    @PostMapping("/change-password")
    public String changePassword(
            @RequestBody ChangePasswordDto dto,
            Principal principal) {
        userService.changePassword(
                principal.getName(),
                dto.getOldPassword(),
                dto.getNewPassword());
        return "Password changed successfully.";
    }
}