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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;

    private Instant userCreatedDate;
    private Instant userUpdateDate;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @PrePersist
    protected void onCreate()
    {
        this.userCreatedDate = Instant.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        this.userUpdateDate = Instant.now();
    }


}