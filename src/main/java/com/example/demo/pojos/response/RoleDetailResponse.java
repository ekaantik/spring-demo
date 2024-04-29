package com.example.demo.pojos.response;

import com.example.iot.entity.AssetDetails;
import com.example.iot.exception.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDetailResponse {

    private UUID id;

    private String roleName;

    private List<AssetDetails> assetDetails;

    private GenericResponse response;
}

