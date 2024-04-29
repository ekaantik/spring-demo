package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/user")
public class CustomerUserDetailController {

    private final UserDetailService userDetailService;

    public CustomerUserDetailController(UserDetailService userDetailService){
        this.userDetailService = userDetailService;
    }

    @PostMapping("/invite")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<UserDetailsResponse> inviteUser(@RequestBody UserDetailsRequest userDetailRequest, @RequestHeader(name = "Authorization") String token) {
        UserDetailsResponse response = userDetailService.createUserInvite(userDetailRequest, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<UserDetailsResponse> updateUser(@RequestBody UserDetailsRequest userDetailRequest,
                                                          @RequestParam("id") UUID id) {
        UserDetailsResponse response = userDetailService.updateUser(userDetailRequest,id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<UserDetailsResponse> getUserProfile(@RequestHeader(name = "Authorization") String token) {
        
        //Get UserDetail by ID
        UserDetailsResponse userDetailsResponse = userDetailService.getUserProfile(token);

        //All Users Found
        if (userDetailsResponse != null) {
            return ResponseEntity.ok(userDetailsResponse);
        }

        //Invalid Request
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid request");
        }
    }



//    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
//    public ResponseEntity<List<UserDetailsResponse>> getUsersProfile(@RequestHeader(name = "Authorization") String token) {
//        List<UserDetailsResponse> userDetailsResponseList = userDetailService.getAllUserProfile(token);
//
////        Customer Found
//        if (userDetailsResponseList != null) return ResponseEntity.ok(userDetailsResponseList);
//
//            //No Customer Found
//        else return ResponseEntity.notFound().build();
//        return null;
//    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<List<UserDetailsResponse>> getAllUsersProfile(@RequestHeader(name = "Authorization") String token) {

        //Get all Users
        List<UserDetailsResponse> userDetailsResponseList = userDetailService.getAllUsersProfile(token);

        //All Users Found
        if (userDetailsResponseList != null) {
            return ResponseEntity.ok(userDetailsResponseList);
        }

        //Invalid Request
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid request");
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<String> deleteUserDetailById(@RequestParam("id") UUID id) {
        
        //Try deleting UserDetail   
        boolean result = userDetailService.deleteUserDetailsById(id);

        //Succesfully deleted User Details
        if (result) {
            return ResponseEntity.ok("User Detail with ID " + id + " has been deleted.");
        }

        //Invalid Request
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid request");
        }
    }

}
