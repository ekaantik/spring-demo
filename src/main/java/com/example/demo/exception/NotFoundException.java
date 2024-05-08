package com.example.demo.exception;

import com.example.demo.constants.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {
    public NotFoundException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}