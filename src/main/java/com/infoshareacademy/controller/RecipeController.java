package com.infoshareacademy.controller;

import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class RecipeController {

    @GetMapping("/recipe")
    @ResponseBody
    public List<Recipe> getAllRecipes(RecipeService recipeService) throws IOException {
        return recipeService.getJson();
    }

    //TODO - wyszukiwanie po części nazwy produktu
    @GetMapping("/recipe/{name}/search")
    @ResponseBody
    public List<Recipe> findByName(@PathVariable String name, RecipeService recipeService) throws IOException {
        List<Recipe> recipe = recipeService.getJson();
        return recipe.stream().filter(list -> list.getName().equalsIgnoreCase(name)).toList();
    }

}
