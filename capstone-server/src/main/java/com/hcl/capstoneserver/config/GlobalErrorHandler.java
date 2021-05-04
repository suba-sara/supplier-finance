package com.hcl.capstoneserver.config;

import com.hcl.capstoneserver.config.error_responses.DefaultErrorResponse;
import com.hcl.capstoneserver.config.error_responses.DefaultValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<DefaultValidationErrorResponse> handleValidationArgumentException(MethodArgumentNotValidException ex) {
        ArrayList<Map<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            LinkedHashMap<String, String> error = new LinkedHashMap<>();
            error.put("field",fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());

            errors.add(error);
        }

        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(HttpStatus.BAD_REQUEST, "Validation Failed", errors),
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<DefaultErrorResponse> handleInvalidArguments(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                new DefaultErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<DefaultErrorResponse> handleClientErrors(HttpClientErrorException ex) {
        return new ResponseEntity<>(
                new DefaultErrorResponse(ex.getStatusCode(), ex.getMessage()),
                ex.getStatusCode()
        );
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<DefaultErrorResponse> unhandledErrors(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                new DefaultErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

