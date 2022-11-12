package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.RecipeAllegrensDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.RecipeAllegrens;
import com.infoshareacademy.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    private final ShoppingListService shoppingListService;

    @Autowired
    public RecipeController(RecipeService recipeService, ShoppingListService shoppingListService) {
        this.recipeService = recipeService;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public String getAllRecipeButCanFilterByMealType(Model model, @Param("meal") Meal meal, @SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable) {
        model.addAttribute("selectedMeal", meal);
        model.addAttribute("shoppingList", shoppingListService.findAllShoppingLists());
        model.addAttribute("recipes", recipeService.getRecipesByCanFilterByMeal(meal, pageable));
        return "recipes";
    }

    @GetMapping("/filtered-prodcts")
    public String searchRecipeList(Model model, @Param("keyword") String keyword, @SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable) {
        model.addAttribute("recipes", recipeService.getSearchRecipe(keyword, pageable));
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
    public String addProduct(@ModelAttribute("recipe")  RecipeDto recipe) {
        recipe.addProduct(new RecipeDto.ProductRecipeDto());
        return "create-recipe";
    }

    @PostMapping(value = "/recipe", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute("recipe")  RecipeDto recipe, HttpServletRequest request) {
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
    public String editAllergensRecipe(@PathVariable Long recipeId, Model model) {
        model.addAttribute("allergens", recipeService.getRecipeById(recipeId).getRecipeAllegrens());
        model.addAttribute("recipeId", recipeId);
        return "edit-recipe-allergens";
    }

    @PostMapping("/{recipeId}/allergens")
    public String saveAllergensRecipe(@PathVariable Long recipeId, @ModelAttribute RecipeAllegrensDto allergens) {
        recipeService.saveRecipeAllergens(recipeId, allergens);
        return "redirect:/recipes/" + recipeId;
    }

    @GetMapping("/recipe/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }

    @GetMapping("/{recipeId}/shoppinglist/{shoppingListId}")
    public String addRecipeToShoppingList(@PathVariable Long recipeId, @PathVariable Long shoppingListId) {
        shoppingListService.addRecipeToShoppingList(recipeService.getRecipeById(recipeId),shoppingListId);
        return "redirect:/recipes";
    }

    @GetMapping("/delete-all-recipes")
    public String deleteAllRecipes(){
        recipeService.deleteAllRecipes();
        return "redirect:/recipes";
    }

    @GetMapping("/recipes-todo")
    public String recipesByProductsInFridge(@SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable, Model model){
        model.addAttribute("recipes",recipeService.getRecipeByProductsInFridge(pageable));

        return "recipes";
    }
}