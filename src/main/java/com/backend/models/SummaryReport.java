package com.backend.models;

import java.time.LocalDate;
import java.util.List;

public class SummaryReport {
    private Period period;
    private Double totalSpent;
    private Double averageDailySpent;
    private Long transactionsCount; // Изменено с Integer на Long
    private List<CategoryReport> topCategories;
    private List<FamilyMemberSummary> familyMembersSummary;

    public SummaryReport() {
    }

    public static class Period {
        private LocalDate startDate;
        private LocalDate endDate;

        public Period() {
        }

        public Period(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
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

    // Геттеры и сеттеры
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Double getAverageDailySpent() {
        return averageDailySpent;
    }

    public void setAverageDailySpent(Double averageDailySpent) {
        this.averageDailySpent = averageDailySpent;
    }

    public Long getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(Long transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    public List<CategoryReport> getTopCategories() {
        return topCategories;
    }

    public void setTopCategories(List<CategoryReport> topCategories) {
        this.topCategories = topCategories;
    }

    public List<FamilyMemberSummary> getFamilyMembersSummary() {
        return familyMembersSummary;
    }

    public void setFamilyMembersSummary(List<FamilyMemberSummary> familyMembersSummary) {
        this.familyMembersSummary = familyMembersSummary;
    }
}