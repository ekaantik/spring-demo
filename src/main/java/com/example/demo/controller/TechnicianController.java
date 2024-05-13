package com.example.demo.controller;

import com.example.demo.pojos.request.TechnicianRequest;
import com.example.demo.pojos.response.TechnicianResponse;
import com.example.demo.service.TechnicianService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/technician")
public class TechnicianController {
    private final TechnicianService technicianService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<TechnicianResponse> createTechnician(@RequestHeader("Authorization") String token,
            @RequestBody TechnicianRequest req) {
        return ResponseEntity.ok(technicianService.createTechnician(token, req));
    }

    @GetMapping("get-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<TechnicianResponse> getTechnicianById(@RequestParam UUID id) {
        return ResponseEntity.ok(technicianService.getTechnicianById(id));
    }

    @DeleteMapping("delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<String> deleteTechnicianById(@RequestParam UUID id) {
        return ResponseEntity.ok(technicianService.deleteTechnicianById(id));
    }

}