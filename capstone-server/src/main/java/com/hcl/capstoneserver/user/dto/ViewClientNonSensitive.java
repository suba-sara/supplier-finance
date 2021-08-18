package com.hcl.capstoneserver.user.dto;

public class ViewClientNonSensitive {
    private String clientId;
    private String name;

    public ViewClientNonSensitive() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
