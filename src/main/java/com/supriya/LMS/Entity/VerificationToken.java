package com.supriya.LMS.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String tokenType; // "EMAIL_VERIFICATION" or "PASSWORD_RESET"

    private LocalDateTime expiryTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryTime);
    }
}