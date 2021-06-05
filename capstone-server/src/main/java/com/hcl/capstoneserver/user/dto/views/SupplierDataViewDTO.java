package com.hcl.capstoneserver.user.dto.views;

public class SupplierDataViewDTO {
    private String supplierId;
    private String name;

    public SupplierDataViewDTO() {
    }

    public SupplierDataViewDTO(String supplierId, String name) {
        this.supplierId = supplierId;
        this.name = name;
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
