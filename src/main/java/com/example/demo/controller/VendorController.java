package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojos.request.UserRequest;
import com.example.demo.pojos.response.UserResponse;
import com.example.demo.security.service.AuthServicesImpl;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/vendor")
public class VendorController {

    private final AuthServicesImpl userService;

    @GetMapping("/get-by-id")
    public ResponseEntity<UserResponse> getVendorById(@RequestParam("id") UUID vendorId) {
        log.info("VendorController getVendorById get vendor request was called");
        return ResponseEntity.ok(userService.getVendorById(vendorId));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateVendorById(@RequestHeader("Authorization") String token,
            @RequestBody UserRequest req) {
        log.info("VendorController updateVendorById update vendor request was called");
        return ResponseEntity.ok(userService.updateVendor(token, req));
    }

}
