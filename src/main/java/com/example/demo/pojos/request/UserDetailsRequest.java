package com.example.demo.pojos.request;

import com.example.demo.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDetailsRequest {
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private String contactNumber;
}