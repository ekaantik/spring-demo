package com.example.demo.pojos.response;

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
public class RoleDetailsResponse {

    private UUID id;

    private String roleName;

    private String description;

    private List<UUID> assetDetailsId;

}