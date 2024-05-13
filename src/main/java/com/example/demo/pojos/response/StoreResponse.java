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
public class StoreResponse {
    private UUID id;
    private String name;
    private String address;
    private String serviceType;
    private UUID vendorUserId;
}
