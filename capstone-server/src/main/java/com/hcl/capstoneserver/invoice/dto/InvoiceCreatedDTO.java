package com.hcl.capstoneserver.invoice.dto;

public class InvoiceCreatedDTO {
    private Integer fileId;
    private String token;
    private ClientViewInvoiceDTO invoice;

    public InvoiceCreatedDTO() {
    }

    public InvoiceCreatedDTO(Integer fileId, String token, ClientViewInvoiceDTO invoice) {
        this.fileId = fileId;
        this.token = token;
        this.invoice = invoice;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ClientViewInvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(ClientViewInvoiceDTO invoice) {
        this.invoice = invoice;
    }
}
