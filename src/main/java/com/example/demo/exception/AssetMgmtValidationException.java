package com.example.demo.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.time.ZonedDateTime;

public class AssetMgmtValidationException extends BaseException {

    @Serial
    private static final long serialVersionUID = 4221404793562936557L;

    public AssetMgmtValidationException() {
        super();
    }

    public AssetMgmtValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ZonedDateTime timestamp, String message1, Integer responseCode, Details details) {
        super(message, cause, enableSuppression, writableStackTrace, timestamp, message1, responseCode, details);
    }

    public AssetMgmtValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }



}
