package com.example.demo.exception;

import java.time.ZonedDateTime;

public class InternalServerErrorException extends BaseException {


    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ZonedDateTime timestamp, String message1, Integer responseCode, Details details) {
        super(message, cause, enableSuppression, writableStackTrace, timestamp, message1, responseCode, details);
    }

    public InternalServerErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
//
//
//    /**
//     * Use the once with Throwable cause
//     *
//     * @param messageFormat
//     * @param args
//     */
//    @Deprecated
//    public InternalServerErrorException(String messageFormat, Object... args) {
//        super(HttpStatus.INTERNAL_SERVER_ERROR, String.format(messageFormat, args));
//    }
//
//    public InternalServerErrorException(String messageFormat, Throwable cause, Object... args) {
//        super(HttpStatus.INTERNAL_SERVER_ERROR, String.format(messageFormat, args), cause);
//    }

}
