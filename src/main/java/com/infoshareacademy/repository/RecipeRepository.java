package com.infoshareacademy.repository;

import com.infoshareacademy.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

//    @Query("SELECT r FROM Recipe r WHERE " +
            //TODO: wyszukiwanie po liscie produktow (składniku/kach)
//            "CONCAT(r.name, r.description, r.preparationTime)" +
//            "LIKE %?1%")
//    public List<Recipe> findRecipeBy(String keyword);


    @Query("SELECT DISTINCT r FROM Recipe r " +
            "LEFT JOIN FETCH r.productList " +
            "WHERE " + "CONCAT(r.name, r.description,r.preparationTime)" + "LIKE %?1%")
    List<Recipe> findRecipeBy(String keyword);
}
