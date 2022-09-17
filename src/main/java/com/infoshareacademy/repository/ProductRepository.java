package com.infoshareacademy.repository;

import com.infoshareacademy.entity.product.ProductInFridge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductInFridge, Long> {
}