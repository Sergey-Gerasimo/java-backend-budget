package com.backend.repository;

import com.backend.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        List<Transaction> findByCategoryId(Long categoryId);

        List<Transaction> findByFamilyMember(String familyMember);

        List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);

        List<Transaction> findByCategoryIdAndDateBetween(Long categoryId, LocalDate startDate, LocalDate endDate);

        @Query("SELECT t FROM Transaction t WHERE " +
                        "(:categoryId IS NULL OR t.categoryId = :categoryId) AND " +
                        "(:startDate IS NULL OR t.date >= :startDate) AND " +
                        "(:endDate IS NULL OR t.date <= :endDate) AND " +
                        "(:familyMember IS NULL OR t.familyMember = :familyMember) " +
                        "ORDER BY t.date DESC, t.createdAt DESC")
        List<Transaction> findByFilters(@Param("categoryId") Long categoryId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("familyMember") String familyMember);

        @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE " +
                        "t.categoryId = :categoryId AND t.date BETWEEN :startDate AND :endDate")
        Double getTotalSpentByCategoryAndPeriod(@Param("categoryId") Long categoryId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
        Double getTotalSpentByPeriod(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        @Query("SELECT COUNT(t) FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
        Long getTransactionsCountByPeriod(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);
}