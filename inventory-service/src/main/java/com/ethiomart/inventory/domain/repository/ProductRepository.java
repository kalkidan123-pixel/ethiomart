package com.ethiomart.inventory.domain.repository;

import com.ethiomart.inventory.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String productId);

    Product save(Product product);
}
