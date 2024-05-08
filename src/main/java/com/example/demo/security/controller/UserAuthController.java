package com.example.demo.security.controller;

import com.example.demo.security.dto.UserAuthRequest;
import com.example.demo.security.dto.UserAuthResponse;
import com.example.demo.security.dto.UserSignUpRequest;
import com.example.demo.security.service.AuthServicesImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public class UserAuthController {

    @Autowired
    public AuthServicesImpl authServices;

    @PostMapping("/sign-up")
    public ResponseEntity<UserAuthResponse> signUp(@RequestBody UserSignUpRequest req) {
        UserAuthResponse response = authServices.performSignUp(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> login(@RequestBody UserAuthRequest req) {
        UserAuthResponse response = authServices.performLogin(req);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(@RequestHeader(name = "Authorization") String token, @RequestBody UserResetPasswordRequest req) {
//        log.info("UserResetPasswordRequest " + req);
//        authServices.resetPassword(token, req);
//        return ResponseEntity.ok("Password changed successfully!");
//    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Health Ok");
    }



}
