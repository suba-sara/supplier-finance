package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Invoice {
    @Id
    @GeneratedValue
    private Integer invoiceId;
    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Client clientId;
    @ManyToOne
    @JoinColumn(name = "supplierId", nullable = false)
    private Supplier supplierId;
    private Integer invoiceNumber;
    private Date invoiceDate;
    private Double amount;
    private Integer status;

    public Invoice() {
    }

    public Invoice(
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

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", clientId=" + clientId +
                ", supplierId=" + supplierId +
                ", invoiceNumber=" + invoiceNumber +
                ", invoiceDate=" + invoiceDate +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
