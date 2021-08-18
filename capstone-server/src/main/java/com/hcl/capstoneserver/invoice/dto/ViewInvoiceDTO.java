package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.invoice.InvoiceStatus;
import com.hcl.capstoneserver.user.dto.ViewClientNonSensitive;
import com.hcl.capstoneserver.user.dto.ViewSupplierNonSensitive;

import java.time.LocalDate;

public class ViewInvoiceDTO {
    private final static String fileUriBase = "/api/getFile/";

    private Integer invoiceId;
    private ViewClientNonSensitive client;
    private ViewSupplierNonSensitive supplier;
    private String fileUrl;
    private String invoiceNumber;
    private LocalDate uploadedDate;
    private LocalDate invoiceDate;
    private Double amount;
    private InvoiceStatus status;
    private CurrencyType currencyType;

    public ViewInvoiceDTO() {
    }


    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ViewClientNonSensitive getClient() {
        return client;
    }

    public void setClient(ViewClientNonSensitive client) {
        this.client = client;
    }

    public ViewSupplierNonSensitive getSupplier() {
        return supplier;
    }

    public void setSupplier(ViewSupplierNonSensitive supplier) {
        this.supplier = supplier;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrlWithToken(Integer fileId, String token) {
        this.fileUrl = fileUriBase + fileId + "?token=" + token;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDate uploadedDate) {
        this.uploadedDate = uploadedDate;
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
