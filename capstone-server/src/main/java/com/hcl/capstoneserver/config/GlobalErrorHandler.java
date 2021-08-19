package com.hcl.capstoneserver.config;

import com.hcl.capstoneserver.config.error_responses.DefaultErrorResponse;
import com.hcl.capstoneserver.config.error_responses.DefaultValidationErrorResponse;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    private Map<String, String> _getErrorsMaps(String field, String message) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("field", field);
        error.put("message", message);
        return error;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request
    ) {
        ArrayList<Map<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(_getErrorsMaps(fieldError.getField(), fieldError.getDefaultMessage()));
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
    public final ResponseEntity<Object> handleClientValidationErrors(HttpClientErrorException ex) {
        //        ArrayList<Map<String, String>> errors = new ArrayList<>();
        //        errors.add(_getErrorsMaps(ex.getStatusText(), ex.getMessage()));
        //        return new ResponseEntity<>(
        //                new DefaultValidationErrorResponse(
        //                        ex.getStatusCode(),
        //                        "Validation Error",
        //                        errors
        //                ),
        //                ex.getStatusCode()
        //        );
        ex.printStackTrace();
        return new ResponseEntity<>(
                new DefaultErrorResponse(ex.getStatusCode(), ex.getStatusText()),
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected final ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(new DefaultErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Max file size exceeded"
        ), HttpStatus.BAD_REQUEST);
    }
}

