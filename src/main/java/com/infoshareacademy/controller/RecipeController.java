package com.infoshareacademy.controller;

import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {

    @GetMapping("/recipe")
    @ResponseBody
    public List<Recipe> getAllRecipes(RecipeService recipeService) throws IOException {
        return recipeService.getJson();
    }

    //TODO - wyszukiwanie po części nazwy produktu
    @GetMapping("/recipe/byname/{name}/search")
    @ResponseBody
    public List<Recipe> findingRecipeByName(@PathVariable String name, RecipeService recipeService) throws IOException {
        List<Recipe> recipe = recipeService.getJson();
        return recipe.stream().filter(list -> list.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("/recipe/bytime/{time}/search")
    @ResponseBody
    public List<Recipe> findingRecipeByTime(@PathVariable Integer time, RecipeService recipeService) throws IOException {
        List<Recipe> recipe = recipeService.getJson();
        return recipe.stream().filter(list -> list.getPreparationTime() <= (time)).collect(Collectors.toList());
    }

}
