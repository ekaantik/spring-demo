package com.example.demo.exception;

import com.example.demo.constants.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler  {
    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<Object> handleSignatureException(SignatureException ex, WebRequest request) {
        return new ResponseEntity<Object>(prepareErrorResponse("Invalid JWT signature"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex, WebRequest request) {
        return new ResponseEntity<Object>(prepareErrorResponse("Malformed JWT token"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        return new ResponseEntity<Object>(prepareErrorResponse("Invalid JWT token"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {UnsupportedJwtException.class})
    public ResponseEntity<Object> handleUnsupportedJwtException(UnsupportedJwtException ex, WebRequest request) {
        return new ResponseEntity<Object>(prepareErrorResponse("Unsupported JWT token"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<Object>("JWT claims string is empty", HttpStatus.UNAUTHORIZED);
    }


    private GenericResponse prepareErrorResponse(String errMsg){
        Details details = Details.builder()
                .appError(ErrorCode.INVALID_TOKEN.getAppError())
                .appErrorCode(ErrorCode.INVALID_TOKEN.getAppErrorCode())
                .appErrorMessage(ErrorCode.INVALID_TOKEN.getAppErrorMessage())
                .build();

        // Build Generic Response
        GenericResponse genericResponse = GenericResponse.builder()
                .timestamp(ZonedDateTime.now())
                .message(errMsg)
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .details(details)
                .build();

        return genericResponse;

    }
}