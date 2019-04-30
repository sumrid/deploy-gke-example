package com.project.product_service.repository;

import com.project.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Long> {
    Optional<Product> findById(long id);
    Optional<Product> findByName(String name);
    Product save(Product pd);
}
