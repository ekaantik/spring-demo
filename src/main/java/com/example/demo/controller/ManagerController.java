package com.example.demo.controller;

import com.example.demo.pojos.request.ManagerRequest;
import com.example.demo.pojos.response.ManagerResponse;
import com.example.demo.service.ManagerService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<ManagerResponse> createManager(@RequestHeader("Authorization") String token,
            @RequestBody ManagerRequest req) {
        return ResponseEntity.ok(managerService.createManager(token, req));
    }

    @GetMapping("get-by-id")
    public ResponseEntity<ManagerResponse> getManagerById(@RequestParam UUID id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @DeleteMapping("delete-by-id")
    public ResponseEntity<String> deleteManagerById(@RequestParam UUID id) {
        return ResponseEntity.ok(managerService.deleteManagerById(id));
    }

}