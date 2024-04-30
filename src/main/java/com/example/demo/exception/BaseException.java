package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.time.ZonedDateTime;

import com.example.demo.constants.ErrorCode;

@Getter
@Setter
@ToString
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6310634067802340672L;

    private final ZonedDateTime timestamp;
    private final String message;
    private final Integer responseCode;
    private final Details details;

    public BaseException() {
        super();
        this.timestamp = null;
        this.message = null;
        this.responseCode = null;
        this.details = null;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            ZonedDateTime timestamp, String message1, Integer responseCode, Details details) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.timestamp = timestamp;
        this.message = message1;
        this.responseCode = responseCode;
        this.details = details;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.timestamp = null;
        this.message = null;
        this.responseCode = null;
        this.details = null;
    }

    public BaseException(String message, Integer responseCode, Details details, Throwable ex) {
        super(message, ex, false, false);
        this.timestamp = null;
        this.message = message;
        this.responseCode = responseCode;
        this.details = details;
    }

    public BaseException(ZonedDateTime timestamp, String message) {
        super(null, null, false, false);
        this.timestamp = timestamp;
        this.message = message;
        this.responseCode = null;
        this.details = null;
    }

    public BaseException(ErrorCode errorCode, Object... args) {
        super(String.format(errorCode.getAppErrorMessage(), args));
        this.timestamp = ZonedDateTime.now();
        this.message = String.format(errorCode.getAppErrorMessage(), args);
        this.responseCode = errorCode.getResponseCode();
        this.details = Details.builder()
                .appError(errorCode.getAppError())
                .appErrorCode(errorCode.getAppErrorCode())
                .appErrorMessage(String.format(errorCode.getAppErrorMessage(), args))
                .build();
    }
}
