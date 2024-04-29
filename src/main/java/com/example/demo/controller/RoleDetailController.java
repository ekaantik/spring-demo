package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@RestController
//@RequestMapping("api/v1/roles")
public class RoleDetailController {

//    private final RoleDetailService roleDetailService;
//
//    @Autowired
//    RoleDetailController(RoleDetailService roleDetailService){
//        this.roleDetailService = roleDetailService;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<RoleDetailResponse> createRoleDetail(@RequestBody RoleDetailRequest roleDetailRequest) {
//        RoleDetailResponse roleDetailResponse = roleDetailService.createRoleDetail(roleDetailRequest);
//        return ResponseEntity.ok(roleDetailResponse);
//    }
//
//    @GetMapping("get-all-roles")
//    public ResponseEntity<List<RoleDetailResponse>> getAllRoleDetail() {
//        List<RoleDetailResponse> roleDetailResponses = roleDetailService.getAllRoleDetails();
//
//        //Role Details Found
//        if (roleDetailResponses != null && !roleDetailResponses.isEmpty())
//            return ResponseEntity.ok(roleDetailResponses);
//
//            //No Role Details Found
//        else return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<RoleDetailResponse> getRoleDetailById(@RequestParam("id") UUID id) {
//
//        RoleDetailResponse roleDetailResponse = roleDetailService.getRoleDetailById(id);
//
//        //Role Detail Found
//        if (roleDetailResponse != null) return ResponseEntity.ok(roleDetailResponse);
//
//            //No Role Detail Found
//        else return ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping
//    public ResponseEntity<String> deleteRoleDetailById(@RequestParam("id") UUID id) {
//       boolean result = roleDetailService.deleteRoleDetailById(id);
//
//        // Successfully deleted Role Detail
//       if (result) return ResponseEntity.ok("Role Detail with ID " + id + " has been deleted.");
//
//        //Invalid Request
//       else return ResponseEntity.badRequest().build();
//    }

}
