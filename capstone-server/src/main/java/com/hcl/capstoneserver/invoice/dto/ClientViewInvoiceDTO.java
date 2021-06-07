package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.invoice.InvoiceStatus;
import com.hcl.capstoneserver.user.dto.views.ClientDataViewDTO;
import com.hcl.capstoneserver.user.dto.views.SupplierDataViewDTO;

public class ClientViewInvoiceDTO {
    private Integer invoiceId;
    private SupplierDataViewDTO supplier;
    private String invoiceNumber;
    private String invoiceDate;
    private Double amount;
    private InvoiceStatus status;
    private CurrencyType currencyType;

    public ClientViewInvoiceDTO() {
    }

    public ClientViewInvoiceDTO(
            Integer invoiceId,
            SupplierDataViewDTO supplier,
            String invoiceNumber,
            String invoiceDate,
            Double amount,
            InvoiceStatus status,
            CurrencyType currencyType
    ) {
        this.invoiceId = invoiceId;
        this.supplier = supplier;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.status = status;
        this.currencyType = currencyType;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public SupplierDataViewDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDataViewDTO supplier) {
        this.supplier = supplier;
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
