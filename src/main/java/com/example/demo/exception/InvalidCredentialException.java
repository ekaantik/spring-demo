package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.constants.ErrorCode;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidCredentialException extends BaseException {
    public InvalidCredentialException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}