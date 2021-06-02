package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.invoice.InvoiceStatus;
import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

public class UpdateInvoiceDTO {
    private Integer invoiceId;
    @NotBlank(message = "supplier id is required")
    private String supplierId;
    @NotBlank(message = "invoice number is required")
    private Integer invoiceNumber;
    @NotBlank(message = "date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String invoiceDate;
    @NotBlank(message = "amount is required")
    @Currency(value = "0", message = "amount is must be greater than")
    private Double amount;
    @NotBlank(message = "currency is required")
    private CurrencyType currencyType;

    public UpdateInvoiceDTO(
            Integer invoiceId,
            String supplierId,
            Integer invoiceNumber,
            String invoiceDate,
            Double amount,
            CurrencyType currencyType
    ) {
        this.invoiceId = invoiceId;
        this.supplierId = supplierId;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
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

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}
