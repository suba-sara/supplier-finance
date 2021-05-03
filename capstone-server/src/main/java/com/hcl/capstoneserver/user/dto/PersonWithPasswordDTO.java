package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.*;

public class PersonWithPasswordDTO extends AppUserWithPasswordDTO{
    @Size(min = 3, max = 30, message = "name must be between 3 to 30 characters")
    private String name = null;

    @Pattern(regexp = "^[a-zA-Z0-9_, ]+$", message = "address must only contain alphanumeric values")
    @NotBlank
    private String address = null;

    @Email(message = "email is not valid")
    @NotNull
    private String email = null;
    private String phone = null;

    @NotBlank
    private Float interestRate = null;

    public PersonWithPasswordDTO() {
    }

    public PersonWithPasswordDTO(String name, String address, String email, String phone, Float interestRate) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.interestRate = interestRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }
}
