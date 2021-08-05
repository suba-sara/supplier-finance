package com.hcl.capstoneserver.config;

import com.hcl.capstoneserver.account.exception.AccountAlreadyHasUser;
import com.hcl.capstoneserver.account.exception.AccountNotFoundException;
import com.hcl.capstoneserver.account.exception.OTPTimedOut;
import com.hcl.capstoneserver.config.error_responses.DefaultErrorResponse;
import com.hcl.capstoneserver.config.error_responses.DefaultValidationErrorResponse;
import com.hcl.capstoneserver.invoice.exception.*;
import com.hcl.capstoneserver.user.exceptions.EmailAlreadyExistsException;
import com.hcl.capstoneserver.user.exceptions.UserAlreadyExistsException;
import com.hcl.capstoneserver.user.exceptions.UserDoesNotExistException;
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
import java.util.List;
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
        ArrayList<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps("Email", ex.getMessage()));

        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        "Email Already Exists.",
                        errors
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected final ResponseEntity<Object> handleUserIdExistsException(UserAlreadyExistsException ex) {
        ArrayList<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps("User Id", ex.getMessage()));

        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(HttpStatus.BAD_REQUEST, "User Already Exists.", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    protected final ResponseEntity<Object> handleUserNotFoundException(UserDoesNotExistException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps(ex.getField(), ex.getMessage()));

        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(HttpStatus.BAD_REQUEST, "User Not Found.", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvoiceNumberAlreadyExistsSupplierException.class)
    protected final ResponseEntity<Object> handleInvoiceNumberAlreadyExistsSupplierException(
            InvoiceNumberAlreadyExistsSupplierException ex
    ) {
        List<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps(ex.getField(), ex.getMessage()));

        return new ResponseEntity<>(
                new DefaultValidationErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        "Invoice Number Is Exists For Supplier.",
                        errors
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvoiceDateOldException.class)
    protected final ResponseEntity<Object> handleInvoiceDateOldException(InvoiceDateOldException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps(ex.getField(), ex.getMessage()));
        return new ResponseEntity<>(new DefaultValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invoice Date Not A Future Date.",
                errors
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvoiceOwnershipException.class)
    protected final ResponseEntity<Object> handleInvoiceOwnershipException(InvoiceOwnershipException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps(ex.getField(), ex.getMessage()));
        return new ResponseEntity<>(new DefaultValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invoice Access Denied.",
                errors
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    protected final ResponseEntity<Object> handleInvoiceNotFoundException(InvoiceNotFoundException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps(ex.getField(), ex.getMessage()));
        return new ResponseEntity<>(new DefaultValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invoice Not Found.",
                errors
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvoiceStatusException.class)
    protected final ResponseEntity<Object> handleInvoiceStatusException(InvoiceStatusException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        errors.add(_getErrorsMaps(ex.getField(), ex.getMessage()));
        return new ResponseEntity<>(new DefaultValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invoice Status Validate.",
                errors
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected final ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(new DefaultErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Max file size exceeded"
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    protected final ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(new DefaultErrorResponse(HttpStatus.BAD_REQUEST, "Bank account not found."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountAlreadyHasUser.class)
    protected final ResponseEntity<Object> handleAccountAlreadyHasUser(AccountAlreadyHasUser ex) {
        return new ResponseEntity<>(new DefaultErrorResponse(HttpStatus.BAD_REQUEST, "The bank account is already verified by a user."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OTPTimedOut.class)
    protected final ResponseEntity<Object> handleOTPTimedOut(OTPTimedOut ex) {
        return new ResponseEntity<>(new DefaultErrorResponse(HttpStatus.BAD_REQUEST, "OTP code timed out."), HttpStatus.BAD_REQUEST);
    }
}

