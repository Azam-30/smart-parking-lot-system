package com.example.smartparking.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.smartparking.entity.User;
import com.example.smartparking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        User user =
                userRepository.findByUsername(
                        username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(
                        user.getUsername())
                .password(
                        user.getPassword())
                .roles(
                        user.getRole())
                .build();
    }
}