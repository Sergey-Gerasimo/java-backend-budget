package com.backend.models;

public class CategoryReport {
    private Long categoryId;
    private String categoryName;
    private Double totalSpent;
    private Integer transactionsCount;
    private Double percentage;

    public CategoryReport() {
    }

    public CategoryReport(Long categoryId, String categoryName, Double totalSpent,
            Integer transactionsCount, Double percentage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalSpent = totalSpent;
        this.transactionsCount = transactionsCount;
        this.percentage = percentage;
    }

    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Integer getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(Integer transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}