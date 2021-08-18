package com.hcl.capstoneserver.user.dto;

public class ClientDTO extends PersonDTO {
    private String clientId;

    public ClientDTO() {
    }

    public ClientDTO(
            String userId,
            String name,
            String address,
            String email,
            String phone,
            String accountNumber,
            String clientId
    ) {
        super(userId, name, address, email, phone, accountNumber);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
