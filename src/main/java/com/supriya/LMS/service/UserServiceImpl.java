package com.supriya.LMS.service;

import com.supriya.LMS.Entity.Providers;
import com.supriya.LMS.Entity.Users;
import com.supriya.LMS.Entity.VerificationToken;
import com.supriya.LMS.dto.ChangePasswordDto;
import com.supriya.LMS.dto.RegisterDto;
import com.supriya.LMS.dto.ResetPasswordDto;
import com.supriya.LMS.repository.UsersRepository;
import com.supriya.LMS.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    // ── 1. REGISTER ──────────────────────────────────────────
    @Override
    public Users register(RegisterDto dto) {
        if (usersRepository.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException(
                    "Email already registered: " + dto.getEmail());
        }

        Users user = Users.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Providers.USER)
                .enabled(false) // not active until email verified
                .build();

        return usersRepository.save(user);
    }

    // ── 2. VERIFY EMAIL ──────────────────────────────────────
    @Override
    public String verifyEmail(String token) {
        VerificationToken vToken = tokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid token"));

        if (vToken.isExpired()) {
            return "Token has expired. Please request a new one.";
        }

        Users user = vToken.getUser();
        user.setEnabled(true);
        usersRepository.save(user);
        tokenRepository.delete(vToken);

        return "Email verified successfully! You can now log in.";
    }

    // ── 3. RESEND TOKEN ──────────────────────────────────────
    @Override
    @Transactional
    public void resendVerificationToken(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Delete old token
        tokenRepository.deleteByUserId(user.getId());

        // Create new token
        String token = UUID.randomUUID().toString();
        VerificationToken vToken = new VerificationToken();
        vToken.setToken(token);
        vToken.setUser(user);
        vToken.setTokenType("EMAIL_VERIFICATION");
        vToken.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(vToken);

        // Send email
        emailService.sendVerificationEmail(
                user.getEmail(), user.getEmail(), token);
    }

    // ── 4. FORGOT PASSWORD ───────────────────────────────────
    @Override
    @Transactional
    public void forgotPassword(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Delete old token
        tokenRepository.deleteByUserId(user.getId());

        // Create reset token
        String token = UUID.randomUUID().toString();
        VerificationToken vToken = new VerificationToken();
        vToken.setToken(token);
        vToken.setUser(user);
        vToken.setTokenType("PASSWORD_RESET");
        vToken.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(vToken);

        // Send email
        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    // ── 5. RESET PASSWORD ────────────────────────────────────
    @Override
    public void resetPassword(ResetPasswordDto dto) {
        VerificationToken vToken =
                tokenRepository.findByToken(dto.getToken())
                        .orElseThrow(() ->
                                new RuntimeException("Invalid or expired token"));

        if (vToken.isExpired()) {
            throw new RuntimeException("Token has expired");
        }

        if (!"PASSWORD_RESET".equals(vToken.getTokenType())) {
            throw new RuntimeException("Invalid token type");
        }

        Users user = vToken.getUser();
        user.setPassword(
                passwordEncoder.encode(dto.getNewPassword()));
        usersRepository.save(user);
        tokenRepository.delete(vToken);
    }

    // ── 6. CHANGE PASSWORD ───────────────────────────────────
    @Override
    public String changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
        return "Successfully Password Change";
    }
}