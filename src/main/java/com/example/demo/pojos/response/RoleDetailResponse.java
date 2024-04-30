package com.example.demo.pojos.response;

import com.example.demo.exception.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDetailResponse {

    private UUID id;

    private String roleName;

    private GenericResponse response;
}

