package com.hcl.capstoneserver.user.dto;

public class ViewClientNonSensitive {
    private String clientId;
    private String name;

    public ViewClientNonSensitive() {
    }

    public String getSupplierId() {
        return clientId;
    }

    public void setSupplierId(String supplierId) {
        this.clientId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
