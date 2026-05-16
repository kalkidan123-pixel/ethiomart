package com.ethiomart.inventory.infrastructure.config;

import com.ethiomart.inventory.infrastructure.persistence.entity.ProductEntity;
import com.ethiomart.inventory.infrastructure.persistence.repository.ProductJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductJpaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new ProductEntity("PROD-001", 100));
                repository.save(new ProductEntity("PROD-002", 5));
            }
        };
    }
}
