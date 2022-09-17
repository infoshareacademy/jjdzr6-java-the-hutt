package com.infoshareacademy.controller;


import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import com.infoshareacademy.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("recipes")
public class RecipeController {

    private RecipeService recipeService;
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipe());
        return "recipes";
    }

    @GetMapping("/search")
    public String searchRecipeList(Model model, @Param("keyword") String keyword) {
        model.addAttribute("recipes", recipeService.getSearchRecipe(keyword));
        model.addAttribute("keyword", keyword);
        return "search-recipe";
    }

    @GetMapping("/new")
    public String createRecipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "create-recipe";
    }

    @PostMapping("/new")
    public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-recipe";
        }
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }


    @PostMapping(value = "/new", params = {"addProduct"})
    public String addProduct(@ModelAttribute("recipe") Recipe recipe) {
        recipe.addProduct(new ProductRecipe());
        return "create-recipe";
    }

    @PostMapping(value = "/new", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute("recipe") Recipe recipe,
                                HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removeProduct"));
        recipe.getProductList().remove(index);
        return "create-recipe";
    }
    //-------------------------------------------

    @GetMapping("/edit/{id}")
    public String editRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "edit-recipe";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateRecipe(@PathVariable Long id, @ModelAttribute("recipe") Recipe recipe, Model model) {

        Recipe existingRecipe = recipeService.getRecipeById(id);
        existingRecipe.setRecipeId(id);
        existingRecipe.setName(recipe.getName());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setPreparationTime(recipe.getPreparationTime());
//TODO
//        existingRecipe.setProductList(recipe.getProductList());

        recipeService.saveRecipe(existingRecipe);
        return "redirect:/recipes";
    }

    //TODO: produkty update
    @PostMapping(value = "/edit/{id}", params = {"addEditProduct"})
    public String addUpdProduct(@ModelAttribute("recipe") Recipe recipe, @PathVariable Long id) {
        recipe = recipeService.getRecipeById(id);
        //TODO
        recipe.addProduct(new ProductRecipe());
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes/edit/" + recipe.getRecipeId();
    }

    @GetMapping(value = "/delete/product/{productId}/recipe/{recipeId}")
    public String removeUpdProduct(@PathVariable Long productId, @PathVariable Long recipeId) {
        Recipe recipe = recipeRepository.getReferenceById(recipeId);

        recipe.getProductList().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst().ifPresent(p -> p.setRecipe(null));
        recipe.getProductList().removeIf(p -> p.getProductId().equals(productId));
        recipeRepository.save(recipe);
        return "redirect:/recipes/edit/" + recipe.getRecipeId();
    }

    @GetMapping("/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }
}
