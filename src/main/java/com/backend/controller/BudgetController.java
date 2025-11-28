package com.backend.controller;

import com.backend.models.Budget;
import com.backend.models.BudgetCreate;
import com.backend.repository.BudgetRepository;
import com.backend.repository.CategoryRepository;
import com.backend.repository.TransactionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/budgets")
@Tag(name = "Budgets", description = "Управление бюджетами")
public class BudgetController {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public BudgetController(BudgetRepository budgetRepository,
            CategoryRepository categoryRepository,
            TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    @Operation(summary = "Получить список бюджетов")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        try {
            List<Budget> budgets = budgetRepository.findAll();
            return ResponseEntity.ok(budgets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить бюджет по ID")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        try {
            return budgetRepository.findById(id)
                    .map(budget -> {
                        // Рассчитываем потраченную сумму
                        Double spent = transactionRepository.getTotalSpentByCategoryAndPeriod(
                                budget.getCategoryId(), budget.getStartDate(),
                                budget.getEndDate() != null ? budget.getEndDate() : java.time.LocalDate.now());
                        // Создаем новый объект с рассчитанной суммой
                        Budget budgetWithSpent = new Budget();
                        budgetWithSpent.setId(budget.getId());
                        budgetWithSpent.setCategoryId(budget.getCategoryId());
                        budgetWithSpent.setAmount(budget.getAmount());
                        budgetWithSpent.setPeriod(budget.getPeriod());
                        budgetWithSpent.setStartDate(budget.getStartDate());
                        budgetWithSpent.setEndDate(budget.getEndDate());
                        budgetWithSpent.setCreatedAt(budget.getCreatedAt());
                        budgetWithSpent.setUpdatedAt(budget.getUpdatedAt());
                        budgetWithSpent.setCategory(budget.getCategory());
                        // Здесь можно установить spent, если добавить поле в entity
                        return ResponseEntity.ok(budgetWithSpent);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Создать новый бюджет")
    public ResponseEntity<Budget> createBudget(@Valid @RequestBody BudgetCreate budgetCreate) {
        try {
            // Проверяем существование категории
            if (!categoryRepository.existsById(budgetCreate.getCategoryId())) {
                return ResponseEntity.badRequest().build();
            }

            // Проверяем, не существует ли уже бюджет для этой категории на этот период
            if (budgetRepository.existsByCategoryIdAndPeriodAndStartDate(
                    budgetCreate.getCategoryId(), budgetCreate.getPeriod(), budgetCreate.getStartDate())) {
                return ResponseEntity.badRequest().build();
            }

            Budget budget = new Budget();
            budget.setCategoryId(budgetCreate.getCategoryId());
            budget.setAmount(budgetCreate.getAmount());
            budget.setPeriod(budgetCreate.getPeriod());
            budget.setStartDate(budgetCreate.getStartDate());
            budget.setEndDate(budgetCreate.getEndDate());

            Budget savedBudget = budgetRepository.save(budget);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBudget);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить бюджет")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        try {
            if (!budgetRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            budgetRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}