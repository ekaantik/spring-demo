package com.example.demo.exception;

import java.io.Serial;
import java.time.ZonedDateTime;

public class RequestValidationException extends BaseException {
    @Serial
    private static final long serialVersionUID = -2004460350289912711L;

    public RequestValidationException() {
        super();
    }

    public RequestValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ZonedDateTime timestamp, String message1, Integer responseCode, Details details) {
        super(message, cause, enableSuppression, writableStackTrace, timestamp, message1, responseCode, details);
    }

    public RequestValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RequestValidationException(String message, int responseCode, Details details) {
        super(message, responseCode, details);

    }
}
