package com.backend.repository.impl;

import com.backend.models.CategoryReport;
import com.backend.models.FamilyMemberSummary;
import com.backend.models.SummaryReport;
import com.backend.repository.ReportRepository;
import com.backend.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final TransactionRepository transactionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ReportRepositoryImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
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
        summaryReport.setTransactionsCount(transactionsCount);

        // Топ категории
        summaryReport.setTopCategories(getCategoryReport(startDate, endDate));

        // Сводка по членам семьи
        summaryReport.setFamilyMembersSummary(getFamilyMembersSummary(startDate, endDate));

        return summaryReport;
    }

    @Override
    public List<CategoryReport> getCategoryReport(LocalDate startDate, LocalDate endDate) {
        String sql = """
                SELECT
                    c.id as category_id,
                    c.name as category_name,
                    COALESCE(SUM(t.amount), 0) as total_spent,
                    COUNT(t.id) as transactions_count,
                    CASE
                        WHEN (SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE date BETWEEN ?1 AND ?2) = 0 THEN 0
                        ELSE CAST((COALESCE(SUM(t.amount), 0) * 100.0 / NULLIF((SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE date BETWEEN ?3 AND ?4), 0)) AS NUMERIC(10,2))
                    END as percentage
                FROM categories c
                LEFT JOIN transactions t ON c.id = t.category_id AND t.date BETWEEN ?5 AND ?6
                GROUP BY c.id, c.name
                ORDER BY total_spent DESC
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);

        List<Object[]> results = query.getResultList();
        List<CategoryReport> reports = new ArrayList<>();

        for (Object[] result : results) {
            CategoryReport report = new CategoryReport();
            report.setCategoryId(((Number) result[0]).longValue());
            report.setCategoryName((String) result[1]);
            report.setTotalSpent(((Number) result[2]).doubleValue());
            report.setTransactionsCount(((Number) result[3]).longValue());
            report.setPercentage(result[4] != null ? ((Number) result[4]).doubleValue() : 0.0);
            reports.add(report);
        }

        return reports;
    }

    @Override
    public List<FamilyMemberSummary> getFamilyMembersSummary(LocalDate startDate, LocalDate endDate) {
        String sql = """
                SELECT
                    family_member,
                    COALESCE(SUM(amount), 0) as total_spent,
                    COUNT(*) as transactions_count
                FROM transactions
                WHERE date BETWEEN ?1 AND ?2
                GROUP BY family_member
                ORDER BY total_spent DESC
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);

        List<Object[]> results = query.getResultList();
        List<FamilyMemberSummary> summaries = new ArrayList<>();

        for (Object[] result : results) {
            FamilyMemberSummary summary = new FamilyMemberSummary();
            summary.setFamilyMember((String) result[0]);
            summary.setTotalSpent(((Number) result[1]).doubleValue());
            summary.setTransactionsCount(((Number) result[2]).longValue());
            summaries.add(summary);
        }

        return summaries;
    }
}