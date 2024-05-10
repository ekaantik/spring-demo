package com.example.demo.controller;

import com.example.demo.entity.Vendor;
import com.example.demo.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    @Autowired
    VendorService vendorService;

    @PostMapping("/add")
    public ResponseEntity<Vendor> vendorAdd(@RequestBody Vendor req)
    {
        return ResponseEntity.ok(vendorService.vendorAdd(req));
    }

    @GetMapping("/get-by-id")
    public Optional<Vendor> vendorGetById(@RequestParam("id") UUID id)
    {
        return vendorService.vendorfindbyid(id);
    }

    @PutMapping("/update/{id}")
    public Vendor vendorUpdate(@PathVariable UUID id, @RequestBody Vendor req)
    {
        return  vendorService.vendorupdate(id,req);
    }
    @DeleteMapping("/delete-by-id")
    public String vendorDeleteById(@RequestParam("id") UUID id)
    {
        return vendorService.vendordeletebyid(id);
    }
}