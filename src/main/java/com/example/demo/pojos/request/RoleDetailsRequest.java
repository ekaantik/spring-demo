package com.example.demo.pojos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoleDetailsRequest {

    private String roleName;

    private String description;

    private List<UUID> assetDetailsId;

}