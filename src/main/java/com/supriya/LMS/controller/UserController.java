package com.supriya.LMS.controller;

import com.supriya.LMS.dto.MembershipRequestDto;
import com.supriya.LMS.dto.UserRequestDto;
import com.supriya.LMS.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRequestDto request) {
        userService.registerUser(request);
        return "User registered successfully";
    }

    @PutMapping("/update")
    public String update(@RequestBody UserRequestDto request) {
        userService.updateUser(request);
        return "User updated successfully";
    }

//    @DeleteMapping("/delete")
//    public String delete(@RequestBody UserRequestDto request) {
//        userService.deleteUser(request);
//        return "User deleted successfully";
//    }

    @GetMapping("/login")
    public String login(@RequestBody UserRequestDto request) {
        userService.loginUser(request);
        return "User logged in successfully";
    }

    @PostMapping("/renew-membership")
    public String renew(@RequestBody MembershipRequestDto request) {
        userService.renewMembership(request);
        return "Membership renewed";
    }

    @PostMapping("/expire-membership")
    public String expire(@RequestBody UserRequestDto request) {
        userService.expireMembership(request);
        return "Membership expired";
    }
}