package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.*;

public class PersonDTO extends AppUserDTO {
    @NotBlank(message = "name is required")
    @Size(min = 3, max = 30, message = "name must be between 3 to 30 characters")
    private String name = null;

    @NotBlank(message = "address is required")
    private String address = null;

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email = null;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^[0-9+ ]+$", message = "phone number is not valid")
    private String phone = null;

    @NotNull(message = "accountNumber is required")
    @Pattern(regexp = "[\\d]{8}", message = "accountNumber is invalid")
    private String accountNumber = null;

    public PersonDTO() {
    }

    public PersonDTO(
            String userId,
            String name,
            String address,
            String email,
            String phone,
            String accountNumber
    ) {
        super(userId);
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.accountNumber = accountNumber;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
