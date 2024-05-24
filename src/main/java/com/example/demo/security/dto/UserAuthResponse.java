package com.example.demo.security.dto;

import com.example.demo.constants.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAuthResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserType userType;
    private String jwtToken;
}
