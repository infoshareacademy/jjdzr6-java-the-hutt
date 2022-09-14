package com.infoshareacademy.repository;

import com.infoshareacademy.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

//TODO: wyswietlanie pełnej listy składników
    @Query("SELECT DISTINCT r FROM Recipe r " +
            "LEFT JOIN FETCH r.productList p " +
            "WHERE " + "CONCAT(r.name, r.description,r.preparationTime, p.productName)" + "LIKE %?1%")
    List<Recipe> findRecipeBy(String keyword);
}
