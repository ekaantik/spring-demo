package com.example.demo.security.controller;

import com.example.demo.security.dto.UserAuthRequest;
import com.example.demo.security.dto.UserAuthResponse;
import com.example.demo.security.dto.UserSignUpRequest;
import com.example.demo.security.service.AuthServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
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

    @PostMapping("refresh-token")
    public ResponseEntity<UserAuthResponse> refreshToken(@RequestParam("token") String token) {
        return ResponseEntity.ok(authServices.refreshToken(token));
    }

    // @PostMapping("/reset-password")
    // public ResponseEntity<String> resetPassword(@RequestHeader(name =
    // "Authorization") String token, @RequestBody UserResetPasswordRequest req) {
    // log.info("UserResetPasswordRequest " + req);
    // authServices.resetPassword(token, req);
    // return ResponseEntity.ok("Password changed successfully!");
    // }

}
