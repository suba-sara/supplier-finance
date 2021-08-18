package com.hcl.capstoneserver.account.dto;

public class GetOtpResponseDTO {
    private String message;

    public GetOtpResponseDTO() {
    }

    public GetOtpResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
