package com.example.demo.pojos.request;

import com.example.demo.constants.ServiceType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StoreRequest {

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private ServiceType serviceType;
}
