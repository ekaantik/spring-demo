package com.example.demo.controller;

import com.example.demo.pojos.request.ManagerRequest;
import com.example.demo.pojos.response.ManagerResponse;
import com.example.demo.service.ManagerService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/manager")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<ManagerResponse> createManager(@RequestHeader("Authorization") String token,
            @RequestBody ManagerRequest req) {
        log.info("ManagerController createManager creating manager request was called");
        return ResponseEntity.ok(managerService.createManager(token, req));
    }

    @GetMapping("/get-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<ManagerResponse> getManagerById(@RequestParam UUID id) {
        log.info("ManagerController getManagerById get manager request was called");
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @DeleteMapping("/delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<String> deleteManagerById(@RequestParam UUID id) {
        log.info("dManagerController deleteManagerById elete manager request was called");
        return ResponseEntity.ok(managerService.deleteManagerById(id));
    }

}