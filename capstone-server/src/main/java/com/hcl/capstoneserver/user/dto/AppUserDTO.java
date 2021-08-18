package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AppUserDTO {
    @NotBlank(message = "userId is required")
    @Size(min = 4, max = 255, message = "userId must be at least 4 characters long")
    private String userId;

    public AppUserDTO() {
    }

    public AppUserDTO(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
