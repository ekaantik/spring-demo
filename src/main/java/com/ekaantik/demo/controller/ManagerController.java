package com.ekaantik.demo.controller;

import com.ekaantik.demo.entity.Manager;
import com.ekaantik.demo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping("/add")
    public ResponseEntity<Manager> manageradd(@RequestBody Manager req)
    {
        return ResponseEntity.ok(managerService.manageradd(req));
    }

    @GetMapping("/get-by-id")
    public Optional<Manager> managergetbyid(@RequestParam("id") UUID id)
    {
        return managerService.managerfindbyid(id);
    }

    @PutMapping("/update/{id}")
    public Manager managerupdate(@PathVariable UUID id,@RequestBody Manager req)
    {
        return  managerService.managerupdate(id,req);
    }
    @DeleteMapping("/delete-by-id")
    public String managerdeletebyid(@RequestParam("id") UUID id)
    {
        return managerService.managerdeletebyid(id);
    }
}
