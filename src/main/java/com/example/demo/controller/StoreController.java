package com.example.demo.controller;

import com.example.demo.pojos.request.StoreRequest;
import com.example.demo.pojos.response.StoreResponse;
import com.example.demo.service.StoreService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/store")
public class StoreController {
    private final StoreService storeService;

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<StoreResponse> createStore(@RequestHeader("Authorization") String token,
            @RequestBody StoreRequest req) {
        log.info("StoreController createStore create store request was called");
        return ResponseEntity.ok(storeService.createStore(token, req));
    }

    @GetMapping("get-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR','MANAGER','TECHNICIAN')")
    public ResponseEntity<StoreResponse> getStoreById(@RequestParam UUID id) {
        log.info("StoreController getStoreById get store request was called");
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @DeleteMapping("delete-by-id")
    @PreAuthorize("hasAnyAuthority('VENDOR')")
    public ResponseEntity<String> deleteStoreById(@RequestParam UUID id) {
        log.info("StoreController deleteStoreById delete store request was called");
        return ResponseEntity.ok(storeService.deleteStoreById(id));
    }

}