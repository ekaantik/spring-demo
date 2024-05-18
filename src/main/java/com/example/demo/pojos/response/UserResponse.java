package com.example.demo.pojos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponse {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userType;
}
