package com.backend.controller;

import com.backend.models.Transaction;
import com.backend.models.TransactionCreate;
import com.backend.models.TransactionUpdate;
import com.backend.repository.TransactionRepository;
import com.backend.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Управление транзакциями (тратами)")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionController(TransactionRepository transactionRepository,
            CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @Operation(summary = "Получить список транзакций")
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String familyMember) {

        try {
            List<Transaction> transactions = transactionRepository.findByFilters(categoryId, startDate, endDate,
                    familyMember);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить транзакцию по ID")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        try {
            return transactionRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Создать новую транзакцию")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionCreate transactionCreate) {
        try {
            // Проверяем существование категории
            if (!categoryRepository.existsById(transactionCreate.getCategoryId())) {
                return ResponseEntity.badRequest().build();
            }

            Transaction transaction = new Transaction();
            transaction.setAmount(transactionCreate.getAmount());
            transaction.setDescription(transactionCreate.getDescription());
            transaction.setCategoryId(transactionCreate.getCategoryId());
            transaction.setFamilyMember(transactionCreate.getFamilyMember());
            transaction.setDate(transactionCreate.getDate());

            Transaction savedTransaction = transactionRepository.save(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить транзакцию")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
            @Valid @RequestBody TransactionUpdate transactionUpdate) {
        try {
            return transactionRepository.findById(id)
                    .map(existingTransaction -> {
                        if (transactionUpdate.getAmount() != null) {
                            existingTransaction.setAmount(transactionUpdate.getAmount());
                        }
                        if (transactionUpdate.getDescription() != null) {
                            existingTransaction.setDescription(transactionUpdate.getDescription());
                        }
                        if (transactionUpdate.getCategoryId() != null) {
                            // Проверяем существование новой категории
                            if (!categoryRepository.existsById(transactionUpdate.getCategoryId())) {
                                throw new RuntimeException("Category not found");
                            }
                            existingTransaction.setCategoryId(transactionUpdate.getCategoryId());
                        }
                        if (transactionUpdate.getFamilyMember() != null) {
                            existingTransaction.setFamilyMember(transactionUpdate.getFamilyMember());
                        }
                        if (transactionUpdate.getDate() != null) {
                            existingTransaction.setDate(transactionUpdate.getDate());
                        }
                        Transaction updatedTransaction = transactionRepository.save(existingTransaction);
                        return ResponseEntity.ok(updatedTransaction);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить транзакцию")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        try {
            if (!transactionRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            transactionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}