package com.hcl.capstoneserver.user.dto;

public class SignUpResponseDTO {
    private String message;

    public SignUpResponseDTO() {
    }

    public SignUpResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
