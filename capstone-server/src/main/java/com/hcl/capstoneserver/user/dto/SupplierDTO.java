package com.hcl.capstoneserver.user.dto;

public class SupplierDTO extends PersonDTO {
    private Integer supplierId;

    public SupplierDTO() {
    }

    public SupplierDTO(String name, String address, String email, String phone, Float interestRate, int supplierId) {
        super(name, address, email, phone, interestRate);
        this.supplierId = supplierId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
