package com.backend.models;

public class FamilyMemberSummary {
    private String familyMember;
    private Double totalSpent;
    private Long transactionsCount;

    // Конструктор по умолчанию
    public FamilyMemberSummary() {
    }

    // Конструктор для JPQL
    public FamilyMemberSummary(String familyMember, Double totalSpent, Long transactionsCount) {
        this.familyMember = familyMember;
        this.totalSpent = totalSpent;
        this.transactionsCount = transactionsCount;
    }

    // Геттеры и сеттеры
    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
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
}