package com.backend.models;

public enum BudgetPeriod {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    YEARLY("yearly");

    private final String value;

    BudgetPeriod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}