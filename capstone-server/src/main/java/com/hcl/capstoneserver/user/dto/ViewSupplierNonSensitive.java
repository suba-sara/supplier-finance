package com.hcl.capstoneserver.user.dto;

public class ViewSupplierNonSensitive {
    private String supplierId;
    private String name;

    public ViewSupplierNonSensitive() {
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
