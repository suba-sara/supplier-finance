package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class InvoiceDTO {
    private Integer invoiceId;
    @NotBlank(message = "client is required")
    private Client clientId;
    @NotBlank(message = "supplier is required")
    private Supplier supplierId;
    @NotBlank(message = "invoice number is required")
    private Integer invoiceNumber;
    @NotBlank(message = "date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date invoiceDate;
    @NotBlank(message = "amount is required")
    @Currency(value = "0", message = "amount is must be greater than")
    private Double amount;
    @NotBlank(message = "status is required")
    private Integer status;

    public InvoiceDTO() {
    }

    public InvoiceDTO(
            Client clientId,
            Supplier supplierId,
            Integer invoiceNumber,
            Date invoiceDate, Double amount, Integer status
    ) {
        this.clientId = clientId;
        this.supplierId = supplierId;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.status = status;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
