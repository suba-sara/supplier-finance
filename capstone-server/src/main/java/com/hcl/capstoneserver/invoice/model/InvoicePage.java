package com.hcl.capstoneserver.invoice.model;

import org.springframework.data.domain.Sort;

public class InvoicePage {
    private Integer pageIndex;
    private Integer pageSize;
    private Sort.Direction sortDirection;
    private String sortBy;

    public InvoicePage() {
        this.pageIndex = 0;
        this.pageSize = 10;
        this.sortDirection = Sort.Direction.ASC;
        this.sortBy = "invoiceId";
    }

    public InvoicePage(Integer pageIndex, Integer pageSize, Sort.Direction sortDirection, String sortBy) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
