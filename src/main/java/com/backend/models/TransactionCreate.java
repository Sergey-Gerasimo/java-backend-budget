package com.backend.models;

import java.time.LocalDate;

public class TransactionCreate {
    private Double amount;
    private String description;
    private Long categoryId;
    private String familyMember;
    private LocalDate date;

    public TransactionCreate() {
    }

    public TransactionCreate(Double amount, String description, Long categoryId,
            String familyMember, LocalDate date) {
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
        this.familyMember = familyMember;
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}