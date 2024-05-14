package com.example.demo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import jakarta.persistence.PersistenceException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

        @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<Map<String, Object>> handleDataAccessException(DataAccessException ex,
                        WebRequest request) {
                Map<String, Object> body = new HashMap<>();
                body.put("timestamp", ZonedDateTime.now());
                body.put("message", "Database error occurred");
                body.put("error", ex.getMessage());

                log.error("DataAccessException: ", ex);

                return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(EmptyResultDataAccessException.class)
        public ResponseEntity<Map<String, Object>> handleEmptyResultDataAccessException(
                        EmptyResultDataAccessException ex, WebRequest request) {
                Map<String, Object> body = new HashMap<>();
                body.put("timestamp", ZonedDateTime.now());
                body.put("message", "No data found");
                body.put("error", ex.getMessage());

                log.error("EmptyResultDataAccessException: ", ex);

                return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
                        DataIntegrityViolationException ex, WebRequest request) {
                Map<String, Object> body = new HashMap<>();
                body.put("timestamp", ZonedDateTime.now());
                body.put("message", "Data integrity violation");
                body.put("error", ex.getMessage());

                log.error("DataIntegrityViolationException: ", ex);

                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(TransactionException.class)
        public ResponseEntity<Map<String, Object>> handleTransactionException(TransactionException ex,
                        WebRequest request) {
                Map<String, Object> body = new HashMap<>();
                body.put("timestamp", ZonedDateTime.now());
                body.put("message", "Transaction failed");
                body.put("error", ex.getMessage());

                log.error("TransactionException: ", ex);

                return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(PersistenceException.class)
        public ResponseEntity<Map<String, Object>> handlePersistanceException(PersistenceException ex,
                        WebRequest request) {
                Map<String, Object> body = new HashMap<>();
                body.put("timestamp", ZonedDateTime.now());
                body.put("message", "Persistance error occurred");
                body.put("error", ex.getMessage());

                log.error("PersistenceException: ", ex);

                return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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

        @ExceptionHandler({ InvalidCredentialException.class })
        public ResponseEntity<GenericResponse> handleInvalidCredentialException(final InvalidCredentialException ex,
                        WebRequest request) {
                // Logging
                log.info("InvalidCredentialException in exception handler", ex);

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.UNAUTHORIZED.value())
                                .details(ex.getDetails())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.UNAUTHORIZED);
        }
}
