package com.hcl.capstoneserver.config.error_responses;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class DefaultValidationErrorResponse extends DefaultErrorResponse{

    private List<Map<String, String>> errors;

    public DefaultValidationErrorResponse() {
    }

    public DefaultValidationErrorResponse(HttpStatus status, String message, List<Map<String, String>> errors) {
        super(status, message);
        this.errors = errors;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }
}