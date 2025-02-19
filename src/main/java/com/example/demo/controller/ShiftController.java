package com.example.demo.controller;

import com.example.demo.pojos.request.ShiftRequest;
import com.example.demo.pojos.response.ShiftResponse;
import com.example.demo.service.ShiftService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/shift")
public class ShiftController {
    private final ShiftService shiftService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<ShiftResponse> createShift(@Valid @RequestBody ShiftRequest req) {
        log.info("ShiftController createShift create shift request was called");
        return ResponseEntity.ok(shiftService.createShift(req));
    }

    @PutMapping("update-by-id/{id}")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<ShiftResponse> upateShift(@PathVariable UUID id, @RequestBody ShiftRequest shiftRequest) {

        log.info("ShiftController updateShift update shift request was called");
        ShiftResponse shiftResponse = shiftService.updateShift(id, shiftRequest);
        return ResponseEntity.ok(shiftResponse);
    }

    @GetMapping("get-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<ShiftResponse> getShiftById(@RequestParam UUID id) {
        log.info("ShiftController getShiftById get shift request was called");
        return ResponseEntity.ok(shiftService.getShiftById(id));
    }

    @GetMapping("get-by-store-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<ShiftResponse> getShiftByStoreId(@RequestParam UUID id) {
        log.info("ShiftController getShiftByStoreId get shift by storeID request was called");
        return ResponseEntity.ok(shiftService.getShiftByStoreId(id));
    }

    @DeleteMapping("delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<Map<String, String>> deleteShiftById(@RequestParam UUID id) {
        log.info("ShiftController deleteShiftById delete shift request was called");
        return shiftService.deleteShiftById(id);
    }

}