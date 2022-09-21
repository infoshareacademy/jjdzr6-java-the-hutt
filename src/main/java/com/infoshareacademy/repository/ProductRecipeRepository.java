package com.infoshareacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.infoshareacademy.entity.product.ProductRecipe;
import java.util.List;

@Repository
public interface ProductRecipeRepository extends JpaRepository<ProductRecipe, Long> {

    List<ProductRecipe> findAllProductsByRecipeRecipeId(Long recipeId);
}
