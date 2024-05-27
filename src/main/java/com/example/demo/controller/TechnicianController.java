package com.example.demo.controller;

import com.example.demo.pojos.request.TechnicianRequest;
import com.example.demo.pojos.response.TechnicianResponse;
import com.example.demo.service.TechnicianService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/technician")
public class TechnicianController {
    private final TechnicianService technicianService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<TechnicianResponse> createTechnician(@RequestHeader("Authorization") String token,
            @Valid @RequestBody TechnicianRequest req) {
        log.info("TechnicianController createTechnician create technician request was called");
        return ResponseEntity.ok(technicianService.createTechnician(token, req));
    }

    @PutMapping("update-by-id/{id}")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<TechnicianResponse> updateTechnicianById(@PathVariable UUID id,
            @RequestBody TechnicianRequest req) {
        log.info("TechnicianController updateTechnicianById update technician request was called");
        return ResponseEntity.ok(technicianService.updateTechnicianById(id, req));
    }

    @GetMapping("get-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<TechnicianResponse> getTechnicianById(@RequestParam UUID id) {
        log.info("TechnicianController getTechnicianById get technician request was called");
        return ResponseEntity.ok(technicianService.getTechnicianById(id));
    }

    @DeleteMapping("delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<String> deleteTechnicianById(@RequestParam UUID id) {
        log.info("TechnicianController deleteTechnicianById delete technician request was called");
        return ResponseEntity.ok(technicianService.deleteTechnicianById(id));
    }

}