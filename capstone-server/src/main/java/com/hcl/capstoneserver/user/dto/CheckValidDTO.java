package com.hcl.capstoneserver.user.dto;

import org.springframework.lang.Nullable;

public class CheckValidDTO {
    private Boolean isValid;
    private String message;

    public CheckValidDTO(Boolean isValid, @Nullable String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
