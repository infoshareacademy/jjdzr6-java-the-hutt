package com.infoshareacademy.repository;

import com.infoshareacademy.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
