package com.backend.repository.impl;

import com.backend.models.CategoryReport;
import com.backend.models.FamilyMemberSummary;
import com.backend.models.SummaryReport;
import com.backend.repository.ReportRepository;
import com.backend.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final TransactionRepository transactionRepository;
    private final com.backend.repository.ReportRepository reportRepositoryJpa;

    public ReportRepositoryImpl(TransactionRepository transactionRepository,
            com.backend.repository.ReportRepository reportRepositoryJpa) {
        this.transactionRepository = transactionRepository;
        this.reportRepositoryJpa = reportRepositoryJpa;
    }

    public SummaryReport getSummaryReport(LocalDate startDate, LocalDate endDate) {
        SummaryReport summaryReport = new SummaryReport();

        // Период
        SummaryReport.Period period = new SummaryReport.Period();
        period.setStartDate(startDate);
        period.setEndDate(endDate);
        summaryReport.setPeriod(period);

        // Основные метрики
        Double totalSpent = transactionRepository.getTotalSpentByPeriod(startDate, endDate);
        Long transactionsCount = transactionRepository.getTransactionsCountByPeriod(startDate, endDate);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        Double averageDailySpent = totalSpent / daysBetween;

        summaryReport.setTotalSpent(totalSpent);
        summaryReport.setAverageDailySpent(averageDailySpent);
        summaryReport.setTransactionsCount(transactionsCount.intValue());

        // Топ категории
        summaryReport.setTopCategories(reportRepositoryJpa.getCategoryReport(startDate, endDate));

        // Сводка по членам семьи
        summaryReport.setFamilyMembersSummary(reportRepositoryJpa.getFamilyMembersSummary(startDate, endDate));

        return summaryReport;
    }

    @Override
    public List<CategoryReport> getCategoryReport(LocalDate startDate, LocalDate endDate) {
        return reportRepositoryJpa.getCategoryReport(startDate, endDate);
    }

    @Override
    public List<FamilyMemberSummary> getFamilyMembersSummary(LocalDate startDate, LocalDate endDate) {
        return reportRepositoryJpa.getFamilyMembersSummary(startDate, endDate);
    }
}