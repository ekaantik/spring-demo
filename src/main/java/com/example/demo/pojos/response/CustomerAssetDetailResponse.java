package com.example.demo.pojos.response;

import com.example.iot.entity.AssetDetails;
import com.example.iot.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerAssetDetailResponse {

    private UUID id;

    private String assetName;

    private String assetDescription;

    private String installationDate;

    private AssetDetails assetDetails;

    private Role role;
}
