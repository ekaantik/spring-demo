package com.ekaantik.demo.controller;

import com.ekaantik.demo.entity.Technician;
import com.ekaantik.demo.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/technician")
public class TechnicianController {

    @Autowired
    TechnicianService technicianService;

    @PostMapping("/add")
    public ResponseEntity<Technician> technicianadd(@RequestBody Technician req) {
        return ResponseEntity.ok(technicianService.technicianadd(req));
    }

    @GetMapping("/get-by-id")
    public Optional<Technician> techniciangetbyid(@RequestParam("id") UUID id) {
        return technicianService.technicianfindbyid(id);
    }

    @PutMapping("/update/{id}")
    public Technician technicianupdate(@PathVariable UUID id, @RequestBody Technician req) {
        return technicianService.technicianupdate(id, req);
    }

    @DeleteMapping("/delete-by-id")
    public String techniciandeletebyid(@RequestParam("id") UUID id) {
        return technicianService.techniciandeletebyid(id);
    }
}
