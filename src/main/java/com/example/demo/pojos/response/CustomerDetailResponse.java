package com.example.demo.pojos.response;


import com.example.demo.constants.CustomerInviteStatus;
import com.example.demo.constants.CustomerServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerDetailResponse {
    private UUID id;
    private String name;
    private String legalName;
    private String customerId;
    private String companyEmail;
    private String contactNumber;
    private String adminEmail;
    private String address;
    private CustomerInviteStatus customerInviteStatus;
    private CustomerServiceStatus customerServiceStatus;
    private String responseMessage;
}
