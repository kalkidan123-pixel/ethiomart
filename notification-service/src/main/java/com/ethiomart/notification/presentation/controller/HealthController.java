package com.ethiomart.notification.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Notification service health")
    public Map<String, String> health() {
        return Map.of("status", "listening");
    }
}
