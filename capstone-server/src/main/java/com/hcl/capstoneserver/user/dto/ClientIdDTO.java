package com.hcl.capstoneserver.user.dto;

public class ClientIdDTO {
    private String clientId;

    public ClientIdDTO(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
