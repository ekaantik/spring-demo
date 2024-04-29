package com.example.demo.constants;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

        // 4XX
        DATA_NULL_OR_EMPTY("Either null or empty value is provided", HttpStatus.BAD_REQUEST.value(),
                        "PAYMENTS-400101", Constants.INVALID_REQUEST, ""),
        INVALID_DATA("Invalid input provided", HttpStatus.BAD_REQUEST.value(), "PAYMENTS-400101",
                        Constants.INVALID_REQUEST, "The value of field %s is invalid!"),

        NOT_EXISTS("Resource not found", HttpStatus.NOT_FOUND.value(), "IOT-404101", "NOT_FOUND",
                        "The value %s of field %s does not exist!"),

        ALREADY_EXISTS("Resource already exists", HttpStatus.CONFLICT.value(), "IOT-409101",
                        "ALREADY_EXISTS", "%s already exists with id %s!"),

        // Authentication and Authorization not in place
        UNAUTHORIZED("Not authorizes to access resources", HttpStatus.UNAUTHORIZED.value(),
                        "PAYMENTS-401101", "UNAUTHORIZED",
                        Constants.IN_THIS_CASE_PASS_MESSAGE_RECEIVED_IN_EXCEPTION_OBJECT),

        // 5XX
        INTERNAL_SERVER_ERROR("Internal Server Error, Something went wrong!",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(), "PAYMENTS-500101", "INTERNAL_SERVER_ERROR",
                        Constants.IN_THIS_CASE_PASS_MESSAGE_RECEIVED_IN_EXCEPTION_OBJECT),

        // Application Error Codes
        SQL_DB_CONNECTION_FAILED("Failed to create connection with postgres DB", null,
                        "PAYMENTS-500101", "postgres_CONNECTION_FAILED", ""),
        SQL_DB_WRITE_FAILED("Exception while writing to postgres DB table", null, "PAYMENTS-500102",
                        "postgres_DB_WRITE_FAILED", "Exception while writing to postgres DB table %s"),
        // External service call errors
        // PAYOUT ERROR CODES
        // 4XX
        // DATA_NULL_OR_EMPTY("Either null or empty value is provided",
        // HttpStatus.BAD_REQUEST.value()
        // , "PAYMENTS-400101", "INVALID_REQUEST", ""),
        // Application Error Codes
        INFLUX_DB_CONNECTION_FAILED("Failed to create connection with influx DB", null, "TIMESERIES-500101",
                        "INFLUX_CONNECTION_FAILED", ""),
        INFLUX_DB_WRITE_FAILED("Exception while writing to influx DB table", null, "TIMESERIES-500102",
                        "INFLUX_DB_WRITE_FAILED", "Exception while writing to influx DB table %s"),

        INVALID_DATA_PAYOUT("Invalid input provided", HttpStatus.BAD_REQUEST.value(), "PAYOUTS-400101",
                        Constants.INVALID_REQUEST, "The value of field %s is invalid!"),
        // NOT_EXISTS ("Resource not
        // found",HttpStatus.NOT_FOUND.value(),"PAYMENTS-404101",
        // "NOT_FOUND", "The value %s of field %s does not exist!"),

        INFLUX_DB_GET_FAILED("Failed to get data from influx DB", null, "TIMESERIES-500103", "INFLUX_DB_GET_FAILED",
                        "Exception while getting date from influx DB table"),
        INFLUX_DB_DATA_NOT_FOUND("No data available for given time frame from influx DB", null, "TIMESERIES-404104",
                        "INFLUX_DB_DATA_NOT_FOUND",
                        "No data available from influx DB for period startTime %s to endTime %s");

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
