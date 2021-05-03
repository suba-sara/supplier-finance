package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

public class PersonDTO extends AppUserDTO {
    @NotBlank(message = "name is required")
    @Size(min = 3, max = 30, message = "name must be between 3 to 30 characters")
    private String name = null;

    @NotBlank(message = "address is required")
    @Pattern(regexp = "^[a-zA-Z0-9_, ]+$", message = "address must only contain alphanumeric values")
    private String address = null;

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email = null;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^[0-9+ ]+$", message = "phone number is not valid")
    private String phone = null;

    @NotNull
    @Min(0)
    private Float interestRate = null;

    public PersonDTO() {
    }

    public PersonDTO(String userId, String name, String address, String email, String phone, Float interestRate) {
        super(userId);
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
