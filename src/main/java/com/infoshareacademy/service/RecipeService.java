package com.infoshareacademy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getSearchRecipe(String keyword) {
        if (keyword != null) {
            return recipeRepository.findRecipeBy(keyword);
        }
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public Recipe saveRecipe(Recipe recipe) {
        recipe.getProductList().forEach(x -> x.setRecipe(recipe));
        return recipeRepository.save(recipe);
    }


    public void updateRecipe(Long recipeId, Recipe recipe) {

        Recipe existingRecipe = recipeRepository.findById(recipeId).get();
        existingRecipe.setRecipeId(recipeId);
        existingRecipe.setName(recipe.getName());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setPreparationTime(recipe.getPreparationTime());
        recipeRepository.save(existingRecipe);
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> sortByPreparationTimeAsc() {
        return recipeRepository.findAll().stream()
                .sorted(Comparator.comparing(Recipe::getPreparationTime))
                .collect(Collectors.toList());
    }

    public List<Recipe> sortByPreparationTimeDesc() {
        return recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "preparationTime"));
    }
}