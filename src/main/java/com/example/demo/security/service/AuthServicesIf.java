package com.example.demo.security.service;

import com.example.demo.security.dto.UserAuthRequest;
import com.example.demo.security.dto.UserAuthResponse;
import com.example.demo.security.dto.UserSignUpRequest;

public interface AuthServicesIf {
    UserAuthResponse performSignUp(UserSignUpRequest req);

    UserAuthResponse performWebAdminLogin(UserAuthRequest req);

    UserAuthResponse performCustomerLogin(UserAuthRequest req);
}
