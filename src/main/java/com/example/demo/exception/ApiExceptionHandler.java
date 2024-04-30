package com.example.demo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // (basePackages = {""})
@Slf4j
public class ApiExceptionHandler { // extends ResponseEntityExceptionHandler {

        // @ExceptionHandler(MethodArgumentNotValidException.class)
        // public ResponseEntity<?> notValid(MethodArgumentNotValidException ex,
        // HttpServletRequest request) {
        // List<String> errors = new ArrayList<>();
        //
        // ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        //
        // Map<String, List<String>> result = new HashMap<>();
        // result.put("errors", errors);
        //
        // return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        // }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
                log.info("handleValidationException...");
                Map<String, String> errors = new HashMap<>();

                // Extract field errors from the exception
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                }

                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
                log.info("handleConstraintViolationException...");
                Map<String, String> errors = new HashMap<>();

                // Extract constraint violations from the exception
                for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                        String fieldName = violation.getPropertyPath().toString();
                        String errorMessage = violation.getMessage();
                        errors.put(fieldName, errorMessage);
                }

                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }


        @ExceptionHandler({ RequestValidationException.class })
        public ResponseEntity<GenericResponse> handleRequestValidationException(final RequestValidationException ex,
                        WebRequest request) {

                // Logging
                log.info("RequestValidationException in exception handler", ex);

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Error Details
                Details details = Details.builder()
                                .appError(ex.getDetails().getAppError())
                                .appErrorCode(ex.getDetails().getAppErrorCode())
                                .appErrorMessage(ex.getDetails().getAppErrorMessage())
                                .build();

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.BAD_REQUEST.value())
                                .details(details)
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.BAD_REQUEST);
        }


        @ExceptionHandler({ NotFoundException.class })
        public ResponseEntity<GenericResponse> handleNotFoundException(final NotFoundException ex, WebRequest request) {

                // Logging
                log.info("NotFoundException in exception handler", ex);

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.NOT_FOUND.value())
                                .details(ex.getDetails())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler({ AlreadyExistsException.class })
        public ResponseEntity<GenericResponse> handleAlreadyExistsException(final AlreadyExistsException ex,
                        WebRequest request) {
                // Logging
                log.info("AlreadyExistsException in exception handler", ex);

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Error Details
                // Details details = Details.builder()
                // .appError(ex.getDetails().getAppError())
                // .appErrorCode(ex.getDetails().getAppErrorCode())
                // .appErrorMessage(ex.getDetails().getAppErrorMessage())
                // .build();

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.CONFLICT.value())
                                .details(ex.getDetails())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.CONFLICT);
        }

}
