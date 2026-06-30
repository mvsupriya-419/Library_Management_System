package com.supriya.LMS.controller;
import com.supriya.LMS.Entity.Users;
import com.supriya.LMS.dto.AuthRequestDTO;
import com.supriya.LMS.dto.AuthResponseDto;
import com.supriya.LMS.dto.ChangePasswordDto;
import com.supriya.LMS.repository.UsersRepository;
import com.supriya.LMS.service.CustomUserDetailsService;
import com.supriya.LMS.service.JwtService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;


    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService customUserDetailsService,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder, UsersRepository usersRepository) {

        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("USER")
                .replace("ROLE_", "");

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        String token = jwtService.generateToken(claims, userDetails);

        return new AuthResponseDto(token, userDetails.getUsername(), role);
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestBody ChangePasswordDto changePasswordDto ,
            Principal principal) {

        String email = principal.getName();
        Users users = usersRepository.findByEmail(email);

        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), users.getPassword()))
        {
            throw new RuntimeException("PASSWORD IS NOT MATCHED.");
        }
        users.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        usersRepository.save(users);
        return "Password changed successfully";
    }
}