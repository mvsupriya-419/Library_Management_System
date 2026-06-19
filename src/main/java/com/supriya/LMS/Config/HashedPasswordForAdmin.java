package com.supriya.LMS.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashedPasswordForAdmin {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoded = new BCryptPasswordEncoder();
        String password = "Admin@123";
        String hashOne = encoded.encode(password);

        System.out.println("ADMIN PASS ON CONSOLE: " + hashOne);
    }
}
