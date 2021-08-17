package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PersonWithPasswordDTO extends PersonDTO implements WithPassword {
    @NotBlank(message = "password is required")
    @Size(min = 4, max = 255, message = "password must be at least 4 characters long")
    private String password;

    @NotNull(message = "OTP is required")
    @Pattern(regexp = "[\\d]{6}", message = "accountNumber is invalid")
    private String OTP = null;

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
            String OTP
    ) {
        super(userId, name, address, email, phone, accountNumber);
        this.password = password;
        this.OTP = OTP;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
