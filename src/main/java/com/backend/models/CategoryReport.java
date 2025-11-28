package com.backend.models;

public class CategoryReport {
    private Long categoryId;
    private String categoryName;
    private Double totalSpent;
    private Long transactionsCount;
    private Double percentage;

    // Конструктор по умолчанию
    public CategoryReport() {
    }

    // Конструктор для JPQL
    public CategoryReport(Long categoryId, String categoryName, Double totalSpent, Long transactionsCount,
            Double percentage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalSpent = totalSpent;
        this.transactionsCount = transactionsCount;
        this.percentage = percentage;
    }

    // Геттеры и сеттеры
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

    public Long getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(Long transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}