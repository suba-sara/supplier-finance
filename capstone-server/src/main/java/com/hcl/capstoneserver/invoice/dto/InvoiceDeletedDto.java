package com.hcl.capstoneserver.invoice.dto;

public class InvoiceDeletedDto {
    private Integer invoiceId;
    private String status;

    public InvoiceDeletedDto() {
    }

    public InvoiceDeletedDto(Integer invoiceId) {
        this.invoiceId = invoiceId;
        this.status = "Deleted";
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
