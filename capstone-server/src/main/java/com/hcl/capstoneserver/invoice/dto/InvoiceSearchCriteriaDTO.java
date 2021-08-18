package com.hcl.capstoneserver.invoice.dto;

import com.hcl.capstoneserver.invoice.CurrencyType;
import com.hcl.capstoneserver.invoice.InvoiceStatus;
import com.hcl.capstoneserver.invoice.model.InvoicePage;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public class InvoiceSearchCriteriaDTO extends InvoicePage {
    private String clientId;
    private String supplierId;
    private Integer invoiceId;
    private String invoiceNumber;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Long ageing;
    private List<InvoiceStatus> status;
    private List<CurrencyType> currencyType;
    private Boolean fetchUploaded;

    public InvoiceSearchCriteriaDTO() {
        super();
    }

    public InvoiceSearchCriteriaDTO(
            Integer pageIndex,
            Integer pageSize,
            Sort.Direction direction,
            String sortBy,
            String clientId,
            String supplierId,
            Integer invoiceId,
            String invoiceNumber,
            LocalDate dateFrom,
            LocalDate dateTo,
            Long ageing,
            List<InvoiceStatus> status,
            List<CurrencyType> currencyType
    ) {
        super(pageIndex, pageSize, direction, sortBy);
        this.clientId = clientId;
        this.supplierId = supplierId;
        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.ageing = ageing;
        this.status = status;
        this.currencyType = currencyType;
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

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Long getAgeing() {
        return ageing;
    }

    public void setAgeing(Long ageing) {
        this.ageing = ageing;
    }

    public List<InvoiceStatus> getStatus() {
        return status;
    }

    public void setStatus(List<InvoiceStatus> status) {
        this.status = status;
    }

    public List<CurrencyType> getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(List<CurrencyType> currencyType) {
        this.currencyType = currencyType;
    }

    public Boolean getFetchUploaded() {
        return fetchUploaded;
    }

    public void setFetchUploaded(Boolean fetchUploaded) {
        this.fetchUploaded = fetchUploaded;
    }
}
