package com.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health Check", description = "API health check endpoints")
public class Health {
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if API is running")
    public String health() {
        return "API is running!";
    }
}
