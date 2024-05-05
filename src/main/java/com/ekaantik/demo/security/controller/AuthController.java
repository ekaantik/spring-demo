package com.ekaantik.demo.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekaantik.demo.security.pojos.request.AuthenticationRequest;
import com.ekaantik.demo.security.pojos.request.RegisterRequest;
import com.ekaantik.demo.security.pojos.response.AuthenticationResponse;
import com.ekaantik.demo.security.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {

        AuthenticationResponse res = authService.authenticate(authRequest);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean emailExists = authService.existsByEmail(email);
        return ResponseEntity.ok(emailExists);
    }

    @GetMapping("/verify-token")
    public ResponseEntity<Boolean> verifyToken(@RequestParam String token) {
        boolean tokenValid = authService.verifyToken(token);
        return ResponseEntity.ok(tokenValid);
    }
}
