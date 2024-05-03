package com.ekaantik.demo.controller;

import com.ekaantik.demo.entity.Manager;
import com.ekaantik.demo.entity.Shift;
import com.ekaantik.demo.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shift")
public class ShiftController {
    @Autowired
    ShiftService shiftService;
    @PostMapping("/add")
    public ResponseEntity<Shift> shiftadd(@RequestBody Shift req)
    {
        return ResponseEntity.ok(shiftService.shiftadd(req));
    }

    @GetMapping("/get-by-id")
    public Optional<Shift> shiftgetbyid(@RequestParam("id") UUID id)
    {
        return shiftService.shiftfindbyid(id);
    }

    @PutMapping("/update/{id}")
    public Shift shiftupdate(@PathVariable UUID id,@RequestBody Shift req)
    {
        return  shiftService.shiftupdate(id,req);
    }
    @DeleteMapping("/delete-by-id")
    public String shiftdeletebyid(@RequestParam("id") UUID id)
    {
        return shiftService.shiftdeletebyid(id);
    }
}
