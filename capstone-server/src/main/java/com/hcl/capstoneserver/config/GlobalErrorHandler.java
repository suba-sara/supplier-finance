package com.hcl.capstoneserver.config;

import com.hcl.capstoneserver.config.error_responses.DefaultErrorResponse;
import com.hcl.capstoneserver.config.error_responses.DefaultValidationErrorResponse;
import com.hcl.capstoneserver.user.exceptions.EmailAlreadyExistsException;
import com.hcl.capstoneserver.user.exceptions.UserAlreadyExistsException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request
    ) {
        ArrayList<Map<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            LinkedHashMap<String, String> error = new LinkedHashMap<>();
            error.put("field", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());

            errors.add(error);
        }

        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(HttpStatus.BAD_REQUEST, "Validation Failed", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request
    ) {
        return new ResponseEntity<>(
                new DefaultErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<DefaultErrorResponse> handleNullArguments(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new DefaultErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<DefaultErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return new ResponseEntity<>(
                new DefaultErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<DefaultErrorResponse> handleClientErrors(HttpClientErrorException ex) {
        return new ResponseEntity<>(
                new DefaultErrorResponse(ex.getStatusCode(), ex.getMessage()),
                ex.getStatusCode()
        );
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> handleExpiredJwtException(JwtException ex) {
        return new ResponseEntity<>(
                new DefaultErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public final ResponseEntity<Object> handleEmailExistsException(EmailAlreadyExistsException ex) {
        ArrayList<Map<String, String>> errors = errorsMap("email", ex.getMessage());
        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(HttpStatus.BAD_REQUEST, "User Already Exists", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected final ResponseEntity<Object> handleUserIdExistsException(UserAlreadyExistsException ex) {
        ArrayList<Map<String, String>> errors = errorsMap("userId", ex.getMessage());
        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(HttpStatus.BAD_REQUEST, "User Already Exists", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    private ArrayList<Map<String, String>> errorsMap(String field, String message) {
        ArrayList<Map<String, String>> errors = new ArrayList<>();
        Map<String, String> error = new LinkedHashMap<>();
        error.put("field", field);
        error.put("message", message);
        errors.add(error);
        return errors;
    }
}

