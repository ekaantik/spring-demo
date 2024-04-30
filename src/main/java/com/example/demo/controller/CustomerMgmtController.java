package com.example.demo.controller;

import com.example.demo.pojos.request.CustomerDetailRequest;
import com.example.demo.pojos.response.CustomerDetailResponse;
import com.example.demo.service.impl.CustomerDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerMgmtController {

    private final CustomerDetailService customerDetailService;

    CustomerMgmtController(CustomerDetailService customerDetailService) {
        this.customerDetailService = customerDetailService;
    }

    @PostMapping("/invite")
    @PreAuthorize("hasAnyAuthority('ROLE_WEB_ADMIN')")
    public ResponseEntity<CustomerDetailResponse> inviteCustomer(@RequestHeader(name = "Authorization") String token,
            @RequestBody CustomerDetailRequest customerDetailRequest) {
        // Create CustomerDetail
        CustomerDetailResponse response = customerDetailService.createCustomerInvite(token, customerDetailRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyAuthority('ROLE_WEB_ADMIN')")
    public ResponseEntity<List<CustomerDetailResponse>> getAllCustomerDetails() {

        // Get all CustomerDetails
        List<CustomerDetailResponse> customerDetailResponse = customerDetailService.getAllCustomerDetails();

        return ResponseEntity.ok().body(customerDetailResponse);
    }

    @GetMapping("/get-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_CUSTOMER_ADMIN','ROLE_WEB_ADMIN')")
    public ResponseEntity<CustomerDetailResponse> getCustomerDetailsById(@RequestParam("id") UUID id) {

        // Get CustomerDetails by ID
        CustomerDetailResponse customerDetailResponse = customerDetailService.getCustomerDetailById(id);

        // CustomerDetails Found
        if (customerDetailResponse != null) {
            return ResponseEntity.ok(customerDetailResponse);
        }

        // No CustomerDetails Found
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }
    }

    @GetMapping("/get-details")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_CUSTOMER_ADMIN')")
    public ResponseEntity<CustomerDetailResponse> getCustomerDetailsByToken(
            @RequestHeader(name = "Authorization") String token) {

        // Get CustomerDetails by Token
        CustomerDetailResponse customerDetailResponse = customerDetailService.getCustomerDetailsByToken(token);

        // CustomerDetails Found
        if (customerDetailResponse != null)
            return ResponseEntity.ok(customerDetailResponse);

        // No CustomerDetails Found
        else
            return ResponseEntity.notFound().build();
    }

    // Only customer admin should be allowed to delete their users
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_WEB_ADMIN')")
    public ResponseEntity<String> deleteCustomerById(@RequestParam("id") UUID id) {

        // Try deleting CustomerDetails
        boolean result = customerDetailService.deleteCustomerById(id);

        // Succesfully deleted CustomerDetails
        if (result) {
            return ResponseEntity.ok("Customer User Details for customer with ID " + id + " has been deleted.");
        }

        // Invalid Request
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }
    }
}
