package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

public class InvoiceDTO {
    private Integer invoiceId;
    @NotBlank(message = "client id is required")
    private String clientId;
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
    @NotBlank(message = "status is required")
    private Integer status;
    @NotBlank(message = "currency is required")
    private CurrencyType currencyType;
    @NotBlank(message = "invoice is required")
    private byte[] invoiceData;

    public InvoiceDTO() {
    }

    public InvoiceDTO(
            String clientId,
            String supplierId,
            Integer invoiceNumber,
            String invoiceDate,
            Double amount,
            Integer status,
            CurrencyType currencyType,
            byte[] invoiceData
    ) {
        this.clientId = clientId;
        this.supplierId = supplierId;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.status = status;
        this.currencyType = currencyType;
        this.invoiceData = invoiceData;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public byte[] getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(byte[] invoiceData) {
        this.invoiceData = invoiceData;
    }
}
