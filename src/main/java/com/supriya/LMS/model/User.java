package com.supriya.LMS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "usersEvent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    private Instant userCreatedDate;

    public User(Long id, String name, String email) {
    }

    public User(String name, String email) {
    }

    @PrePersist
    protected void onCreate()
    {
        this.userCreatedDate = Instant.now();
    }

}