package com.hcl.capstoneserver.user.dto;

public class CheckExistsDTO {
    boolean isValid;

    public CheckExistsDTO() {
    }

    public CheckExistsDTO(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
