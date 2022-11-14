package com.infoshareacademy.repository;

import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {


    @Query("SELECT DISTINCT r FROM Recipe r " +
            "LEFT JOIN FETCH r.productList p " +
            "WHERE " + "CONCAT(r.name, r.description,r.preparationTime, p.productName)" + "LIKE %?1%")
    List<Recipe> findRecipeBy(String keyword);


    @Modifying
    void deleteByRecipeId(Long id);

    @Query("SELECT DISTINCT r FROM Recipe r WHERE r.meal = ?1")
    Page<Recipe> findRecipeByMeal(Meal meal, Pageable pageable);


}
