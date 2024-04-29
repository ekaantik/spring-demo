package com.example.demo.exception;

import com.example.demo.constants.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends BaseException {

    public AlreadyExistsException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}
