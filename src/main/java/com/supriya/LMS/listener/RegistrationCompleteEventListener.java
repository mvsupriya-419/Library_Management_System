package com.supriya.LMS.listener;

import com.supriya.LMS.Entity.Users;
import com.supriya.LMS.Entity.VerificationToken;
import com.supriya.LMS.event.RegistrationCompleteEvent;
import com.supriya.LMS.repository.VerificationTokenRepository;
import com.supriya.LMS.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        Users user = event.getUser();

        // Delete old token if exists
        tokenRepository.deleteByUserId(user.getId());

        // Generate new token
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setTokenType("EMAIL_VERIFICATION");
        verificationToken.setExpiryTime(
                LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(verificationToken);

        // Send email
        emailService.sendVerificationEmail(
                user.getEmail(), user.getEmail(), token);
    }
}