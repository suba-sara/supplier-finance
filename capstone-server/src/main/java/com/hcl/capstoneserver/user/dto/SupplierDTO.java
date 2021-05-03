package com.hcl.capstoneserver.user.dto;

public class SupplierDTO extends PersonDTO {
    private String supplierId;

    public SupplierDTO() {
    }

    public SupplierDTO(String userId, String name, String address, String email, String phone, Float interestRate,
                       String supplierId) {
        super(userId, name, address, email, phone, interestRate);
        this.supplierId = supplierId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
