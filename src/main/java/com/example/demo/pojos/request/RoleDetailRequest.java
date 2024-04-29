package com.example.demo.pojos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class RoleDetailRequest {

    private String role;

    private UUID customerUserDetailId;

    private List<UUID> plantLocationDetailIds;
}
