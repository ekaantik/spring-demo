package com.example.demo.security.dto;

import com.example.demo.pojos.response.CustomerDetailResponse;
import com.example.demo.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAuthResponse {
    private String email;
    private UserStatus userStatus;
    private String jwtToken;
    private CustomerDetailResponse customerDetailResponse;

}
