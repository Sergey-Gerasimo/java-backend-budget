package com.backend.models;

public class FamilyMemberSummary {
    private String familyMember;
    private Double totalSpent;
    private Integer transactionsCount;

    public FamilyMemberSummary() {
    }

    public FamilyMemberSummary(String familyMember, Double totalSpent, Integer transactionsCount) {
        this.familyMember = familyMember;
        this.totalSpent = totalSpent;
        this.transactionsCount = transactionsCount;
    }

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

    public Integer getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(Integer transactionsCount) {
        this.transactionsCount = transactionsCount;
    }
}