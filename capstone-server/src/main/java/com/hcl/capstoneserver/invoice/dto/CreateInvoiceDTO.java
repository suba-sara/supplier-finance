package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.invoice.InvoiceStatus;
import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class CreateInvoiceDTO {
    @NotBlank(message = "supplier id is required")
    private String supplierId;
    @NotBlank(message = "invoice number is required")
    private String invoiceNumber;
    @NotBlank(message = "date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String invoiceDate;
    @NotBlank(message = "amount is required")
    @Currency(value = "0", message = "amount is must be greater than")
    private Double amount;
    @NotBlank(message = "status is required")
    private InvoiceStatus status;
    @NotBlank(message = "currency is required")
    private CurrencyType currencyType;

    public CreateInvoiceDTO() {
    }

    public CreateInvoiceDTO(
            String supplierId,
            String invoiceNumber,
            String invoiceDate,
            Double amount,
            CurrencyType currencyType
    ) {
        this.supplierId = supplierId;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.status = InvoiceStatus.UPLOADED;
        this.currencyType = currencyType;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}
