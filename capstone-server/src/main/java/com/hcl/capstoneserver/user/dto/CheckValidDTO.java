package com.hcl.capstoneserver.user.dto;

public class CheckValidDTO {
    private Boolean isValid;

    public CheckValidDTO(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
