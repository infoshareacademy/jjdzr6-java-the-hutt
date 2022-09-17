package com.infoshareacademy.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.other.Json;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


@Service
public class RecipeService {



    public void writeJson(List<Recipe> recipe) throws IOException {
        Json.writeJson(recipe, "recipe.json");
    }

    public List<Recipe> getJson() throws IOException {
        Path path = Path.of("src", "resources", "recipe.json");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path.toString());
        List<Recipe> recipe = objectMapper.readValue(file, new TypeReference<List<Recipe>>() {
        });
        return recipe;
    }

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getSearchRecipe(String keyword) {
        if(keyword != null){
            return recipeRepository.findRecipeBy(keyword);
        }
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public Recipe saveRecipe(Recipe recipe) {
        recipe.getProductList().forEach(x-> x.setRecipe(recipe));
        return recipeRepository.save(recipe);
    }

        public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

}