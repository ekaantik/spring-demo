package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.constants.ErrorCode;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends BaseException {
    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
