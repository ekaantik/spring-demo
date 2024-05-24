package com.example.demo.constants;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

        // 4XX
        DATA_NULL_OR_EMPTY("Either null or empty value is provided", HttpStatus.BAD_REQUEST.value(),
                        "APP-400101", Constants.INVALID_REQUEST, ""),
        INVALID_DATA("Invalid input provided", HttpStatus.BAD_REQUEST.value(), "APP-400101",
                        Constants.INVALID_REQUEST, "The value of field %s is invalid!"),

        INVALID_TOKEN("Invalid token provided", HttpStatus.UNAUTHORIZED.value(), "APP-401101", "INVALID_TOKEN",
                        "The token provided is invalid!"),

        NOT_EXISTS("Resource not found", HttpStatus.NOT_FOUND.value(), "APP-404101", "NOT_FOUND",
                        "The value %s of field %s of table %s does not exist!"),

        ALREADY_EXISTS("Resource already exists", HttpStatus.CONFLICT.value(), "APP-409101",
                        "ALREADY_EXISTS", "%s already exists with id %s!"),

        // Authentication and Authorization not in place
        UNAUTHORIZED("Not authorizes to access resources", HttpStatus.UNAUTHORIZED.value(),
                        "APP-401101", "UNAUTHORIZED",
                        Constants.IN_THIS_CASE_PASS_MESSAGE_RECEIVED_IN_EXCEPTION_OBJECT),

        // 5XX
        INTERNAL_SERVER_ERROR("Internal Server Error, Something went wrong!",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(), "APP-500101", "INTERNAL_SERVER_ERROR",
                        Constants.IN_THIS_CASE_PASS_MESSAGE_RECEIVED_IN_EXCEPTION_OBJECT),

        // Application Error Codes
        SQL_DB_CONNECTION_FAILED("Failed to create connection with postgres DB", null,
                        "APP-500101", "postgres_CONNECTION_FAILED", ""),
        SQL_DB_WRITE_FAILED("Exception while writing to postgres DB table", null, "APP-500102",
                        "postgres_DB_WRITE_FAILED", "Exception while writing to postgres DB table %s"),
        INVALID_DATA_PAYOUT("Invalid input provided", HttpStatus.BAD_REQUEST.value(), "PAYOUTS-400101",
                        Constants.INVALID_REQUEST, "The value of field %s is invalid!");

        private final String message;

        private final Integer responseCode;

        private final String appError;

        private final String appErrorCode;

        private final String appErrorMessage;

        ErrorCode(String message, Integer responseCode, String appError, String appErrorCode,
                        String appErrorMessage) {
                this.message = message;
                this.responseCode = responseCode;
                this.appError = appError;
                this.appErrorCode = appErrorCode;
                this.appErrorMessage = appErrorMessage;
        }

        @UtilityClass
        private static class Constants {

                public static final String IN_THIS_CASE_PASS_MESSAGE_RECEIVED_IN_EXCEPTION_OBJECT = "%s";
                public static final String INVALID_REQUEST = "INVALID_REQUEST";

        }
}
