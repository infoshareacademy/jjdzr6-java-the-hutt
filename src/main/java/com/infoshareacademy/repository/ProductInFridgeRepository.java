package com.infoshareacademy.repository;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductInFridgeRepository extends JpaRepository<ProductInFridge, Long> {

    @Query("SELECT DISTINCT p FROM ProductInFridge p WHERE p.fridge.fridgeId = ?1")
    Page<ProductInFridge> findProductInFridgeByFridge(Long id, Pageable pageable);
}