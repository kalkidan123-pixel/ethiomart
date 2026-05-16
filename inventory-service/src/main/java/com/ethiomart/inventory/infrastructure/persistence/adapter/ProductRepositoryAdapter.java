package com.ethiomart.inventory.infrastructure.persistence.adapter;

import com.ethiomart.inventory.domain.model.Product;
import com.ethiomart.inventory.domain.repository.ProductRepository;
import com.ethiomart.inventory.infrastructure.persistence.entity.ProductEntity;
import com.ethiomart.inventory.infrastructure.persistence.repository.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Product> findById(String productId) {
        return jpaRepository.findById(productId).map(e -> new Product(e.getId(), e.getStockQuantity()));
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = jpaRepository.findById(product.getId())
                .orElse(new ProductEntity(product.getId(), product.getStockQuantity()));
        entity.setStockQuantity(product.getStockQuantity());
        jpaRepository.save(entity);
        return product;
    }
}
