package com.backend.controller;

import com.backend.models.CategoryReport;
import com.backend.models.SummaryReport;
import com.backend.repository.ReportRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "Отчеты и аналитика")
public class ReportController {

    private final ReportRepository reportRepository;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/summary")
    @Operation(summary = "Получить сводный отчет")
    public ResponseEntity<SummaryReport> getSummaryReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        try {
            // Валидация дат
            if (startDate.isAfter(endDate)) {
                return ResponseEntity.badRequest().build();
            }

            SummaryReport report = reportRepository.getSummaryReport(startDate, endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/by-category")
    @Operation(summary = "Получить отчет по категориям")
    public ResponseEntity<List<CategoryReport>> getCategoryReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        try {
            // Валидация дат
            if (startDate.isAfter(endDate)) {
                return ResponseEntity.badRequest().build();
            }

            List<CategoryReport> report = reportRepository.getCategoryReport(startDate, endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}