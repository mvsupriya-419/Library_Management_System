package com.supriya.LMS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail,
                                      String firstName,
                                      String token) {
        String verifyUrl =
                "http://localhost:8080/auth/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify Your Email - LMS");
        message.setText("Hi " + firstName + ",\n\n"
                + "Click the link below to verify your account:\n"
                + verifyUrl + "\n\n"
                + "This link expires in 10 minutes.\n\n"
                + "Library Management System");

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String toEmail, String token) {
        String resetUrl =
                "http://localhost:8080/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset Your Password - LMS");
        message.setText("Click the link below to reset your password:\n"
                + resetUrl + "\n\n"
                + "This link expires in 10 minutes.\n\n"
                + "If you didn't request this, ignore this email.");

        mailSender.send(message);
    }
}