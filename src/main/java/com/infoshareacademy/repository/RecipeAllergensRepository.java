package com.infoshareacademy.repository;

import com.infoshareacademy.entity.recipe.RecipeAllegrens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeAllergensRepository extends JpaRepository<RecipeAllegrens, Long> {
}
