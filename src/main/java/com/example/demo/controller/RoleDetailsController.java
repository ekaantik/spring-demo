package com.example.demo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/role-details")
public class RoleDetailsController {

    private final RoleDetailsService roleDetailsService;

    public RoleDetailsController(RoleDetailsService roleDetailsService) {
        this.roleDetailsService = roleDetailsService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<RoleDetailsResponse> createRoleDetails(@RequestHeader(name = "Authorization") String token,
            @RequestBody RoleDetailsRequest roleDetailsRequest) {

        System.out.println(" Teesting");

        // Create RoleDetails
        RoleDetailsResponse roleDetailsResponse = roleDetailsService.createRoleDetails(token, roleDetailsRequest);

        return ResponseEntity.ok(roleDetailsResponse);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<RoleDetailsResponse> updateRoleDetails(@RequestHeader(name = "Authorization") String token,
            @RequestBody RoleDetailsRequest roleDetailsRequest) {

        // Update RoleDetails
        RoleDetailsResponse roleDetailsResponse = roleDetailsService.updateRoleDetails(token, roleDetailsRequest);

        return ResponseEntity.ok(roleDetailsResponse);
    }

    @GetMapping("/get-by-role-name")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<Role> getRoleDetailsByRoleName(@RequestHeader(name = "Authorization") String token,
            @RequestParam("roleName") String roleName) {

        Role role = roleDetailsService.getRoleDetails(token, roleName);

        return ResponseEntity.ok(role);
    }
}
