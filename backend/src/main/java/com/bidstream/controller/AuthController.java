package com.bidstream.controller;

import com.bidstream.dto.request.LoginRequest;
import com.bidstream.dto.request.SignupRequest;
import com.bidstream.dto.response.AuthResponse;
import com.bidstream.model.User;
import com.bidstream.security.JwtTokenProvider;
import com.bidstream.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        User newUser = userService.registerUser(request);
        // Auto-login after signup — generate token immediately
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(
                new AuthResponse(token, newUser.getId(), newUser.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
        String token = tokenProvider.generateToken(auth);
        User user = userService.getUserByEmail(request.getEmail());
        return ResponseEntity.ok(
                new AuthResponse(token, user.getId(), user.getUsername()));
    }
}