package com.infoshareacademy.controller;


import com.infoshareacademy.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;

@Controller
public class RecipeController {
//    //----------------------JSON----------------------
//    @GetMapping("/recipesJson")
//    @ResponseBody
//    public List<Recipe> getAllJsonRecipes(RecipeService recipeService) throws IOException {
//        return recipeService.getJson();
//    }
//
//    @GetMapping("/recipesJson/{name}/searchbyname")
//    @ResponseBody
//    public List<Recipe> findingJsonRecipeByName(@PathVariable String name, RecipeService recipeService) throws IOException {
//        List<Recipe> recipe = recipeService.getJson();
//        return recipe.stream().filter(list -> list.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
////        return recipe.stream().filter(list -> list.getName().equalsIgnoreCase(name)).toList();
//    }
//
//    @GetMapping("/recipesJson/{time}/searchbytime")
//    @ResponseBody
//    public List<Recipe> findingJsonRecipeByTime(@PathVariable Integer time, RecipeService recipeService) throws IOException {
//        List<Recipe> recipe = recipeService.getJson();
//        return recipe.stream().filter(list -> list.getPreparationTime() <= (time)).collect(Collectors.toList());
//    }

    //----------------------WEB----------------------
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipe());
        return "recipes";
    }

}

