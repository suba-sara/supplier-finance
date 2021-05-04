package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PersonWithPasswordDTO extends PersonDTO implements WithPassword {
    @NotBlank(message = "password is required")
    @Size(min = 4, max = 255, message = "password must be at least 4 characters long")
    private String password;

    public PersonWithPasswordDTO() {
    }

    public PersonWithPasswordDTO(String userId, String password, String name, String address, String email, String phone,
                                 Float interestRate) {
        super(userId, name, address, email, phone, interestRate);
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
