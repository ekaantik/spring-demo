package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.constants.ErrorCode;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileException extends BaseException {
    public InvalidFileException(ErrorCode errorCode, Object... args) {
        super(errorCode);
    }
}
