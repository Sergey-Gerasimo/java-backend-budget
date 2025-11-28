package com.backend.repository;

import com.backend.models.Budget;
import com.backend.models.BudgetPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByCategoryId(Long categoryId);

    List<Budget> findByPeriod(BudgetPeriod period);

    Optional<Budget> findByCategoryIdAndPeriodAndStartDate(Long categoryId, BudgetPeriod period, LocalDate startDate);

    @Query("SELECT b FROM Budget b WHERE b.startDate <= :date AND (b.endDate IS NULL OR b.endDate >= :date)")
    List<Budget> findActiveBudgets(@Param("date") LocalDate date);

    @Query("SELECT b FROM Budget b WHERE b.period = :period AND b.startDate <= :date AND (b.endDate IS NULL OR b.endDate >= :date)")
    List<Budget> findActiveBudgetsByPeriod(@Param("period") BudgetPeriod period, @Param("date") LocalDate date);

    boolean existsByCategoryIdAndPeriodAndStartDate(Long categoryId, BudgetPeriod period, LocalDate startDate);
}