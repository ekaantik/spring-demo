package com.example.demo.pojos.request;


import com.example.demo.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerDetailRequest {
    private String name;
    private String legalName;
    private String companyEmail;
    private String contactNumber;
    private String adminEmail;
    private String address;
    private String firstName;
    private String lastName;
    private UserStatus userStatus;
}
