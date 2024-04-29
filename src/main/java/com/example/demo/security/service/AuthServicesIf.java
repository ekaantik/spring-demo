package com.example.demo.security.service;

import com.example.iot.security.dto.UserAuthRequest;
import com.example.iot.security.dto.UserAuthResponse;
import com.example.iot.security.dto.UserSignUpRequest;

public interface AuthServicesIf {
    UserAuthResponse performSignUp(UserSignUpRequest req);

    UserAuthResponse performWebAdminLogin(UserAuthRequest req);

    UserAuthResponse performCustomerLogin(UserAuthRequest req);
}
