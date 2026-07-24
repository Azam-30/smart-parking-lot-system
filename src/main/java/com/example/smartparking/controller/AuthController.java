package com.example.smartparking.controller;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.smartparking.dto.*;
import com.example.smartparking.entity.User;
import com.example.smartparking.repository.UserRepository;
import com.example.smartparking.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        User user =
                User.builder()
                        .username(
                                request.getUsername())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()))
                        .role("USER")
                        .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        String token =
                jwtUtil.generateToken(
                        request.getUsername());

        return new LoginResponse(token);
    }
}