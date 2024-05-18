package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojos.response.UserResponse;
import com.example.demo.security.service.AuthServicesImpl;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    private final AuthServicesImpl userService;

    @GetMapping("/get-by-id")
    public ResponseEntity<UserResponse> getVendorById(@RequestParam("id") UUID vendorId) {
        return ResponseEntity.ok(userService.getVendorById(vendorId));
    }
}
