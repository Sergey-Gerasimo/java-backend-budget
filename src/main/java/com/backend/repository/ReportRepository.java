package com.backend.repository;

import com.backend.models.CategoryReport;
import com.backend.models.FamilyMemberSummary;
import com.backend.models.SummaryReport;
import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {

        SummaryReport getSummaryReport(LocalDate startDate, LocalDate endDate);

        List<CategoryReport> getCategoryReport(LocalDate startDate, LocalDate endDate);

        List<FamilyMemberSummary> getFamilyMembersSummary(LocalDate startDate, LocalDate endDate);
}