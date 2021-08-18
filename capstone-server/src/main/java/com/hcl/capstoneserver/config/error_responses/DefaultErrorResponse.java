package com.hcl.capstoneserver.config.error_responses;

import org.springframework.http.HttpStatus;

public class DefaultErrorResponse {
    private HttpStatus status;
    private String message;

    public DefaultErrorResponse() {
    }

    public DefaultErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status.value();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
