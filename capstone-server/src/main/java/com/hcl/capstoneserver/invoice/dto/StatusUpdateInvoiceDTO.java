package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.InvoiceStatus;

import javax.validation.constraints.NotBlank;

public class StatusUpdateInvoiceDTO {
    @NotBlank(message = "invoice id is required")
    private Integer invoiceId;
    @NotBlank(message = "status is required")
    private InvoiceStatus status;

    public StatusUpdateInvoiceDTO(Integer invoiceId, InvoiceStatus status) {
        this.invoiceId = invoiceId;
        this.status = status;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
