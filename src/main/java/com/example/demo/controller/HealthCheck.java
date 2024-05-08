package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/healthcheck")
@Slf4j
public class HealthCheck {

    @GetMapping("/health")
    @PreAuthorize("hasAnyAuthority('ADMIN')") //'ROLE_ADMIN'
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Health Ok From Secure Endpoint");
    }

}
