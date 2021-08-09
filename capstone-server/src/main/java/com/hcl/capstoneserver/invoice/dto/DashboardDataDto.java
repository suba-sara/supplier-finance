package com.hcl.capstoneserver.invoice.dto;

public class DashboardDataDto {
    private int uploadedCount;
    private int inReviewCount;
    private int rejectedCount;
    private int approvedCount;

    public DashboardDataDto() {
    }

    public DashboardDataDto(int uploadedCount, int inReviewCount, int rejectedCount, int approvedCount) {
        this.uploadedCount = uploadedCount;
        this.inReviewCount = inReviewCount;
        this.rejectedCount = rejectedCount;
        this.approvedCount = approvedCount;
    }

    public int getUploadedCount() {
        return uploadedCount;
    }

    public void setUploadedCount(int uploadedCount) {
        this.uploadedCount = uploadedCount;
    }

    public int getInReviewCount() {
        return inReviewCount;
    }

    public void setInReviewCount(int inReviewCount) {
        this.inReviewCount = inReviewCount;
    }

    public int getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public int getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(int approvedCount) {
        this.approvedCount = approvedCount;
    }
}
