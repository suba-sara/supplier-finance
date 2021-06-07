package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class UpdateInvoiceDTO {
    @NotBlank(message = "invoice id is required")
    private Integer invoiceId;
    @NotBlank(message = "supplier id is required")
    private String supplierId;
    @NotBlank(message = "invoice number is required")
    private String invoiceNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate invoiceDate;
    @NotBlank(message = "amount is required")
    @Currency(value = "0", message = "amount is must be greater than")
    private Double amount;
    @NotBlank(message = "currency is required")
    private CurrencyType currencyType;

    public UpdateInvoiceDTO() {
    }

    public UpdateInvoiceDTO(
            Integer invoiceId,
            String supplierId,
            String invoiceNumber,
            LocalDate invoiceDate,
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

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
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

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
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
