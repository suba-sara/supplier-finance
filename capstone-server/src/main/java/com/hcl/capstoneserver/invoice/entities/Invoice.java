package com.hcl.capstoneserver.invoice.entities;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Invoice {
    @Id
    @GeneratedValue
    private Integer invoiceId;
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client clientId;
    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    private Supplier supplierId;
    @Column(unique = true)
    private Integer invoiceNumber;
    private String invoiceDate;
    private Double amount;
    private Integer status;
    private CurrencyType currencyType;

    public Invoice() {
    }

    public Invoice(
            Client clientId,
            Supplier supplierId,
            Integer invoiceNumber,
            String invoiceDate,
            Double amount,
            Integer status,
            CurrencyType currencyType
    ) {
        this.clientId = clientId;
        this.supplierId = supplierId;
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

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", clientId=" + clientId +
                ", supplierId=" + supplierId +
                ", invoiceNumber=" + invoiceNumber +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", currencyType=" + currencyType +
                '}';
    }
}
