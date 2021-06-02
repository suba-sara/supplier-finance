package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class UpdateInvoiceDTO {
    @NotBlank(message = "invoice id is required")
    private Integer invoiceId;
    private String supplierId;
    private String invoiceNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String invoiceDate;
    @Currency(value = "0", message = "amount is must be greater than")
    private Double amount;
    private CurrencyType currencyType;

    public UpdateInvoiceDTO(
            Integer invoiceId,
            String supplierId,
            String invoiceNumber,
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

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}
