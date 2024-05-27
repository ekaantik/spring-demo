package com.example.demo.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PessimisticLockException;

@RestControllerAdvice // (basePackages = {""})
@Slf4j
public class ApiExceptionHandler extends Exception {

        @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<GenericResponse> handleDataAccessException(DataAccessException ex,
                        WebRequest request) {

                // Logging
                log.info("DataAccessException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .details(Details.builder()
                                                .appError("DataAccessException")
                                                .appErrorCode("500")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(EmptyResultDataAccessException.class)
        public ResponseEntity<GenericResponse> handleEmptyResultDataAccessException(
                        EmptyResultDataAccessException ex, WebRequest request) {

                // Logging
                log.info("EmptyResultDataAccessException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.NOT_FOUND.value())
                                .details(Details.builder()
                                                .appError("EmptyResultDataAccessException")
                                                .appErrorCode("404")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.NOT_FOUND);

        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<GenericResponse> handleDataIntegrityViolationException(
                        DataIntegrityViolationException ex, WebRequest request) {

                // Logging
                log.info("DataIntegrityViolationException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .details(Details.builder()
                                                .appError("DataIntegrityViolationException")
                                                .appErrorCode("500")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(TransactionException.class)
        public ResponseEntity<GenericResponse> handleTransactionException(TransactionException ex,
                        WebRequest request) {

                // Logging
                log.info("TransactionException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response

                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .details(Details.builder()
                                                .appError("TransactionException")
                                                .appErrorCode("500")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(TransactionSystemException.class)
        public ResponseEntity<GenericResponse> handleTransactionSystemException(TransactionSystemException ex,
                        WebRequest request) {

                // Logging
                log.info("TransactionSystemException in exception handler {}", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .details(Details.builder()
                                                .appError("TransactionSystemException")
                                                .appErrorCode("500")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers,
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<GenericResponse> handleConstraintViolationException(ConstraintViolationException ex) {

                // Logging
                log.info("ConstraintViolationException in exception handler {}", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Error Details
                Details details = Details.builder()
                                .appError("ConstraintViolationException")
                                .appErrorCode("400")
                                .appErrorMessage(ex.getMessage())
                                .build();

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message("Constraint Violation Exception")
                                .responseCode(HttpStatus.BAD_REQUEST.value())
                                .details(details)
                                .build();
                return new ResponseEntity<>(genericResponse, headers, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(PersistenceException.class)
        public ResponseEntity<GenericResponse> handlePersistanceException(PersistenceException ex) {

                // Logging
                log.info("PersistenceException in exception handler {}", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .details(Details.builder()
                                                .appError("PersistenceException")
                                                .appErrorCode("500")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<GenericResponse> handleValidationException(MethodArgumentNotValidException ex) {

                // Logging
                log.info("MethodArgumentNotValidException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Error Details
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                });

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message("Validation Exception")
                                .responseCode(HttpStatus.BAD_REQUEST.value())
                                .details(Details.builder()
                                                .appError("MethodArgumentNotValidException")
                                                .appErrorCode("400")
                                                .appErrorMessage(errors.toString())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({ RequestValidationException.class })
        public ResponseEntity<GenericResponse> handleRequestValidationException(final RequestValidationException ex,
                        WebRequest request) {

                // Logging
                log.info("RequestValidationException in exception handler");

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
                log.info("NotFoundException in exception handler {} ", ex.getMessage());

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
                log.info("AlreadyExistsException in exception handler {} ", ex.getMessage());

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
                log.info("InvalidCredentialException in exception handler {} ", ex.getMessage());

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

        @ExceptionHandler({ InvalidTokenException.class })
        public ResponseEntity<GenericResponse> handleInvalidTokenException(final InvalidTokenException ex,
                        WebRequest request) {
                // Logging
                log.info("InvalidTokenException in exception handler {} ", ex.getMessage());

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

        @ExceptionHandler({ IllegalArgumentException.class })
        public ResponseEntity<GenericResponse> handleIllegalArgumentException(final IllegalArgumentException ex,
                        WebRequest request) {
                // Logging
                log.info("IllegalArgumentException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.BAD_REQUEST.value())
                                .details(Details.builder()
                                                .appError("IllegalArgumentException")
                                                .appErrorCode("400")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({ HttpMessageNotReadableException.class })
        public ResponseEntity<GenericResponse> handleHttpMessageNotReadableException(
                        final HttpMessageNotReadableException ex,
                        WebRequest request) {
                // Logging
                log.info("HttpMessageNotReadableException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.BAD_REQUEST.value())
                                .details(Details.builder()
                                                .appError("HttpMessageNotReadableException")
                                                .appErrorCode("400")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({ PessimisticLockException.class, LockTimeoutException.class, PersistenceException.class })
        public ResponseEntity<GenericResponse> handlePersistenceException(final PersistenceException ex,
                        WebRequest request) {
                // Logging
                log.info("PersistenceException in exception handler {} ", ex.getMessage());

                // Build Header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Build Generic Response
                GenericResponse genericResponse = GenericResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .message(ex.getMessage())
                                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .details(Details.builder()
                                                .appError("PersistenceException")
                                                .appErrorCode("500")
                                                .appErrorMessage(ex.getMessage())
                                                .build())
                                .build();

                return new ResponseEntity<>(genericResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
