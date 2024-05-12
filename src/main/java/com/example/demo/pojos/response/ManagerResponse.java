package com.example.demo.pojos.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ManagerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UUID vendorId;
}
