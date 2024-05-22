package com.example.demo.controller;

import com.example.demo.pojos.request.ShiftScheduleRequest;
import com.example.demo.pojos.response.ShiftScheduleResponse;
import com.example.demo.service.ShiftScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/shift-schedule")
public class ShiftScheduleController {
    private final ShiftScheduleService shiftScheduleService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<ShiftScheduleResponse> createShiftScheduleSchedule(@RequestBody ShiftScheduleRequest req) {
        log.info("ShiftScheduleController createShiftScheduleSchedule create shift schedule request was called");
        return ResponseEntity.ok(shiftScheduleService.createShiftSchedule(req));
    }

    @GetMapping("get-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<ShiftScheduleResponse> getShiftScheduleScheduleById(@RequestParam UUID id) {
        log.info("ShiftScheduleController getShiftScheduleScheduleById get shift schedule request was called");
        return ResponseEntity.ok(shiftScheduleService.getShiftScheduleById(id));
    }

    @PutMapping("update-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<ShiftScheduleResponse> updateShiftScheduleScheduleById(@RequestParam UUID id,
            @RequestBody ShiftScheduleRequest req) {
        log.info("ShiftScheduleController updateShiftScheduleScheduleById update shift schedule request was called");
        return ResponseEntity.ok(shiftScheduleService.updateShiftScheduleById(id, req));
    }

    @DeleteMapping("delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER')")
    public ResponseEntity<String> deleteShiftScheduleScheduleById(@RequestParam UUID id) {
        log.info("ShiftScheduleController deleteShiftScheduleScheduleById delete shift schedule request was called");
        return ResponseEntity.ok(shiftScheduleService.deleteShiftScheduleById(id));
    }

}