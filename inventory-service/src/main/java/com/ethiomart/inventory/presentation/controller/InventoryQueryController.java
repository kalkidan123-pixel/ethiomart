package com.ethiomart.inventory.presentation.controller;

import com.ethiomart.inventory.domain.repository.ProductRepository;
import com.ethiomart.inventory.presentation.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory")
public class InventoryQueryController {

    private final ProductRepository productRepository;

    public InventoryQueryController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product stock")
    public ResponseEntity<ProductResponse> get(@PathVariable String productId) {
        return productRepository.findById(productId)
                .map(p -> new ProductResponse(p.getId(), p.getStockQuantity()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
