package com.example.demo.exception;

import com.example.demo.constants.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends BaseException {

    public InvalidTokenException(ErrorCode errorCode, Object... args ) {
        super(errorCode);
    }
}
