package com.example.demo.pojos.response;

import com.example.demo.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDetailsResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private String contactNumber;
    private String customerName;
    private String customerAddress;

}
