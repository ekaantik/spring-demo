package com.example.demo.pojos.request;

import com.example.demo.constants.ServiceType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StoreRequest {

    private String name;
    private String address;
    private ServiceType serviceType;
}
