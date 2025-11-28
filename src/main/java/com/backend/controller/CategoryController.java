package com.backend.controller;

import com.backend.models.Category;
import com.backend.models.CategoryCreate;
import com.backend.models.CategoryUpdate;
import com.backend.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Управление категориями трат")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @Operation(summary = "Получить список всех категорий")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAllOrderedByName();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить категорию по ID")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            return categoryRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Создать новую категорию")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryCreate categoryCreate) {
        try {
            // Проверяем, существует ли категория с таким именем
            if (categoryRepository.existsByName(categoryCreate.getName())) {
                return ResponseEntity.badRequest().build();
            }

            Category category = new Category();
            category.setName(categoryCreate.getName());
            category.setDescription(categoryCreate.getDescription());
            category.setColor(categoryCreate.getColor());

            Category savedCategory = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить категорию")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
            @Valid @RequestBody CategoryUpdate categoryUpdate) {
        try {
            return categoryRepository.findById(id)
                    .map(existingCategory -> {
                        if (categoryUpdate.getName() != null) {
                            // Проверяем, не занято ли новое имя другой категорией
                            if (!existingCategory.getName().equals(categoryUpdate.getName()) &&
                                    categoryRepository.existsByName(categoryUpdate.getName())) {
                                throw new RuntimeException("Category name already exists");
                            }
                            existingCategory.setName(categoryUpdate.getName());
                        }
                        if (categoryUpdate.getDescription() != null) {
                            existingCategory.setDescription(categoryUpdate.getDescription());
                        }
                        if (categoryUpdate.getColor() != null) {
                            existingCategory.setColor(categoryUpdate.getColor());
                        }
                        Category updatedCategory = categoryRepository.save(existingCategory);
                        return ResponseEntity.ok(updatedCategory);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить категорию")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            if (!categoryRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}