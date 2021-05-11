package com.hcl.capstoneserver.user.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ClientDTO extends PersonDTO {
    private String clientId;

    @NotBlank(message = "account number is required")
    @Length(max = 10, min = 10, message = "account number is not valid")
    @Pattern(regexp = "^[0-9]+$", message = "account number is not valid")
    private int accountNumber;

    public ClientDTO() {
    }

    public ClientDTO(String userId,
                     String name,
                     String address,
                     String email,
                     String phone,
                     Float interestRate,
                     String clientId, int accountNumber) {
        super(userId, name, address, email, phone, interestRate);
        this.clientId      = clientId;
        this.accountNumber = accountNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
