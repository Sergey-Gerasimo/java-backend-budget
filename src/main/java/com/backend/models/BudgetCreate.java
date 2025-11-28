package com.backend.models;

import java.time.LocalDate;

public class BudgetCreate {
    private Long categoryId;
    private Double amount;
    private BudgetPeriod period;
    private LocalDate startDate;
    private LocalDate endDate;

    public BudgetCreate() {
    }

    public BudgetCreate(Long categoryId, Double amount, BudgetPeriod period, LocalDate startDate) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.period = period;
        this.startDate = startDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BudgetPeriod getPeriod() {
        return period;
    }

    public void setPeriod(BudgetPeriod period) {
        this.period = period;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}