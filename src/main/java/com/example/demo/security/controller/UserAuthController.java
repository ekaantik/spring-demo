package com.example.demo.security.controller;

import com.example.demo.security.dto.UserAuthRequest;
import com.example.demo.security.dto.UserAuthResponse;
import com.example.demo.security.dto.UserSignUpRequest;
import com.example.demo.security.service.AuthServicesImpl;

import com.example.demo.service.s3.S3FileRenameService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class UserAuthController {

    @Autowired
    public AuthServicesImpl authServices;

    private final S3FileRenameService s3FileRenameService;

    @Autowired
    public UserAuthController(S3FileRenameService s3FileRenameService) {
        this.s3FileRenameService = s3FileRenameService;
    }

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

    @PostMapping("/login/test")
    public ResponseEntity<String> login() {
        System.out.println("rename files request was called");

//        UserAuthResponse response = authServices.performLogin(req);
        try {
            s3FileRenameService.renameFiles();
            System.out.println("File renaming operation completed successfully");
//            return ResponseEntity.ok("File renaming operation completed successfully.");
        } catch (Exception e) {
            System.out.println(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during file renaming: " + e.getMessage()));
        }
        return ResponseEntity.ok("the response is ok");
//        return ResponseEntity.ok(response);
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

    // TODO : Remove this unnecessary endpoint
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Health Ok");
    }

}
