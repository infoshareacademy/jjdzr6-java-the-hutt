package com.infoshareacademy.repository;

import com.infoshareacademy.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r from Recipe WHERE r.name LIKE %?1%")
    public List<Recipe> findRecipeBy(String keyword);
}
