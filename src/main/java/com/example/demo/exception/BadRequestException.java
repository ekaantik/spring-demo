package com.example.demo.exception;

import java.time.ZonedDateTime;

public class BadRequestException extends BaseException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ZonedDateTime timestamp, String message1, Integer responseCode, Details details) {
        super(message, cause, enableSuppression, writableStackTrace, timestamp, message1, responseCode, details);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
