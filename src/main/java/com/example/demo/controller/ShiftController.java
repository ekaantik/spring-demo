package com.example.demo.controller;

import com.example.demo.pojos.request.ShiftRequest;
import com.example.demo.pojos.response.ShiftResponse;
import com.example.demo.service.ShiftService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shift")
public class ShiftController {
    private final ShiftService ShiftService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<ShiftResponse> createShift(@RequestBody ShiftRequest req) {
        return ResponseEntity.ok(ShiftService.createShift(req));
    }

    @GetMapping("get-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<ShiftResponse> getShiftById(@RequestParam UUID id) {
        return ResponseEntity.ok(ShiftService.getShiftById(id));
    }

    @DeleteMapping("delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<String> deleteShiftById(@RequestParam UUID id) {
        return ResponseEntity.ok(ShiftService.deleteShiftById(id));
    }

}