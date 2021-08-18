package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonWithPasswordDTO extends PersonDTO implements WithPassword {
    @NotBlank(message = "password is required")
    @Size(min = 4, max = 255, message = "password must be at least 4 characters long")
    private String password;

    @NotNull(message = "otp is required")
    private String otp;

    public PersonWithPasswordDTO() {
    }

    public PersonWithPasswordDTO(
            String userId,
            String name,
            String address,
            String email,
            String phone,
            String accountNumber,
            String password,
            String otp
    ) {
        super(userId, name, address, email, phone, accountNumber);
        this.password = password;
        this.otp = otp;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
