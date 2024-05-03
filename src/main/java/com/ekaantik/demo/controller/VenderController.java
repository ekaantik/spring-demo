package com.ekaantik.demo.controller;

import com.ekaantik.demo.entity.Vender;
import com.ekaantik.demo.service.VenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vender")
public class VenderController {

    @Autowired
    VenderService venderService;

    @PostMapping("/add")
    public ResponseEntity<Vender> venderadd(@RequestBody Vender req)
    {
        return ResponseEntity.ok(venderService.venderadd(req));
    }

    @GetMapping("/get-by-id")
    public Optional<Vender> vendergetbyid(@RequestParam("id") UUID id)
    {
        return venderService.venderfindbyid(id);
    }

    @PutMapping("/update/{id}")
    public Vender venderupdate(@PathVariable UUID id,@RequestBody Vender req)
    {
        return  venderService.venderupdate(id,req);
    }
    @DeleteMapping("/delete-by-id")
    public String venderdeletebyid(@RequestParam("id") UUID id)
    {
        return venderService.venderdeletebyid(id);
    }
}
