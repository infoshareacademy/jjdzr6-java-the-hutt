package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.RecipeAllergensDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.service.FoodPreferencesService;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.RecipeService;
import com.infoshareacademy.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final ShoppingListService shoppingListService;
    private final FoodPreferencesService foodPreferencesService;
    private final FridgeService fridgeService;

    @Autowired
    public RecipeController(RecipeService recipeService, ShoppingListService shoppingListService, FoodPreferencesService foodPreferencesService, FridgeService fridgeService) {
        this.recipeService = recipeService;
        this.shoppingListService = shoppingListService;
        this.foodPreferencesService = foodPreferencesService;
        this.fridgeService = fridgeService;
    }

    @GetMapping
    public String getAllRecipeByMealType(Model model, @Param("meal") Meal meal, @SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable) {
        model.addAttribute("selectedMeal", meal);
        model.addAttribute("shoppingList", shoppingListService.findAllShoppingLists());
        model.addAttribute("recipes", recipeService.getRecipesFilteredByMeal(meal, pageable));
        return "recipes";
    }

    @GetMapping("/keyword")
    public String filterRecipesByKeyword(Model model, @Param("keyword") String keyword, @SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable) {
        model.addAttribute("recipes", recipeService.getSearchedRecipe(keyword, pageable));
        model.addAttribute("keyword", keyword);
        return "recipes";
    }

    @GetMapping("/recipe")
    public String createRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        return "create-recipe";
    }

    @PostMapping("/recipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeDto recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-recipe";
        }
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @PostMapping(value = "/recipe", params = {"addProduct"})
    public String addProduct(@ModelAttribute("recipe") RecipeDto recipe) {
        recipe.addProduct(new RecipeDto.ProductRecipeDto());
        return "create-recipe";
    }

    @PostMapping(value = "/recipe", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute("recipe") RecipeDto recipe, HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removeProduct"));
        recipe.getProductList().remove(index);
        return "create-recipe";
    }

    @GetMapping("/{recipeId}")
    public String editRecipe(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(recipeId));
        return "edit-recipe";
    }

    @PostMapping(value = "/{recipeId}")
    public String updateRecipe(@PathVariable Long recipeId, @ModelAttribute("recipe") RecipeDto recipe) {
        recipeService.updateRecipe(recipeId, recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/{recipeId}/allergens")
    public String editRecipeAllergens(@PathVariable Long recipeId, Model model) {
        model.addAttribute("allergens", recipeService.getRecipeById(recipeId).getRecipeAllergens());
        model.addAttribute("recipeId", recipeId);
        return "edit-recipe-allergens";
    }

    @PostMapping("/{recipeId}/allergens")
    public String saveRecipeAllergens(@PathVariable Long recipeId, @ModelAttribute RecipeAllergensDto allergens) {
        recipeService.saveRecipeAllergens(recipeId, allergens);
        return "redirect:/recipes/" + recipeId;
    }

    @GetMapping("/recipe/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }

    @GetMapping("/{recipeId}/shopping-list/{shoppingListId}")
    public String addRecipeToShoppingList(@PathVariable Long recipeId, @PathVariable Long shoppingListId) {
        shoppingListService.addRecipeToShoppingList(recipeId, shoppingListId);
        return "redirect:/recipes";
    }

    @GetMapping("/delete-all-recipes")
    public String deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return "redirect:/recipes";
    }

    @GetMapping("/fridge")
    public String recipesByProductsInFridge(@SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable, Model model) {
        model.addAttribute("recipes", recipeService.getRecipesFilteredByProductsInFridge(pageable));
        return "recipes";
    }

    @GetMapping("/food-preferences")
    public String getRecipesByFoodPreferences(Model model, @SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable) {
        model.addAttribute("recipes", foodPreferencesService.filterRecipeByFoodPreferences(pageable));
        return "recipes";
    }
}