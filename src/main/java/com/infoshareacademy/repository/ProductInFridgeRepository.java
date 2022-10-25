package com.infoshareacademy.repository;

import com.infoshareacademy.entity.product.ProductInFridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductInFridgeRepository extends JpaRepository<ProductInFridge, Long> {
}