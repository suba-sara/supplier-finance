package com.hcl.capstoneserver.invoice.entities;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.invoice.InvoiceStatus;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Invoice {
    @Id
    @GeneratedValue
    private Integer invoiceId;
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    private Supplier supplier;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private Double amount;
    private InvoiceStatus status;
    private CurrencyType currencyType;

    public Invoice() {
    }

    public Invoice(
            Client client,
            Supplier supplier,
            String invoiceNumber,
            LocalDate invoiceDate,
            Double amount,
            InvoiceStatus status,
            CurrencyType currencyType
    ) {
        this.client = client;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", clientId=" + client +
                ", supplierId=" + supplier +
                ", invoiceNumber=" + invoiceNumber +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", currencyType=" + currencyType +
                '}';
    }
}
