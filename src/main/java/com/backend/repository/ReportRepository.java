package com.backend.repository;

import com.backend.models.CategoryReport;
import com.backend.models.FamilyMemberSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository {

    @Query("SELECT NEW com.backend.model.CategoryReport(" +
            "c.id, c.name, COALESCE(SUM(t.amount), 0), COUNT(t.id), " +
            "CASE WHEN (SELECT COALESCE(SUM(t2.amount), 0) FROM Transaction t2 WHERE t2.date BETWEEN :startDate AND :endDate) = 0 THEN 0 "
            +
            "ELSE (COALESCE(SUM(t.amount), 0) * 100.0 / (SELECT COALESCE(SUM(t2.amount), 0) FROM Transaction t2 WHERE t2.date BETWEEN :startDate AND :endDate)) END) "
            +
            "FROM Category c " +
            "LEFT JOIN Transaction t ON c.id = t.categoryId AND t.date BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id, c.name " +
            "ORDER BY COALESCE(SUM(t.amount), 0) DESC")
    List<CategoryReport> getCategoryReport(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT NEW com.backend.model.FamilyMemberSummary(" +
            "t.familyMember, COALESCE(SUM(t.amount), 0), COUNT(t.id)) " +
            "FROM Transaction t " +
            "WHERE t.date BETWEEN :startDate AND :endDate " +
            "GROUP BY t.familyMember " +
            "ORDER BY COALESCE(SUM(t.amount), 0) DESC")
    List<FamilyMemberSummary> getFamilyMembersSummary(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}