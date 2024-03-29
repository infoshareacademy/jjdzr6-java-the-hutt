package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.ProductRecipeDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.service.ProductRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class ProductRecipeController {
    private final ProductRecipeService productRecipeService;

    @Autowired
    public ProductRecipeController(ProductRecipeService productRecipeService) {
        this.productRecipeService = productRecipeService;
    }

    @GetMapping("/{recipeId}/products")
    public String editProductRecipeList(@PathVariable Long recipeId, Model model) {
        List<ProductRecipeDto> productRecipeList = productRecipeService.getAllProductRecipeByRecipeId(recipeId);
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("productList", productRecipeList);
        model.addAttribute("productRecipe", productRecipeList.stream().findFirst().get());
        return "edit-product-recipe";
    }

    @PostMapping("/{recipeId}/products")
    public String saveProductRecipe(@PathVariable Long recipeId, @ModelAttribute("productRecipe") RecipeDto.ProductRecipeDto productRecipe, @ModelAttribute("recipe") RecipeDto recipe, Model model) {
        model.addAttribute("recipeId", recipeId);
        productRecipeService.saveProductRecipe(productRecipe, recipe);
        return "redirect:/recipes/" + recipeId + "/products";
    }

    @GetMapping("/{recipeId}/products/{productId}")
    public String editProductRecipeForm(@PathVariable Long recipeId, @PathVariable Long productId, Model model) throws Exception {
        model.addAttribute("productRecipe", productRecipeService.findProductRecipeById(productId));
        model.addAttribute("recipeId", recipeId);
        return "edit-product-recipe-form";
    }

    @PostMapping("/{recipeId}/products/{productId}")
    public String saveProductRecipeForm(@PathVariable Long recipeId, @ModelAttribute("productRecipe") RecipeDto.ProductRecipeDto productRecipe, @ModelAttribute("recipe") RecipeDto recipe, Model model) {
        model.addAttribute("recipeId", recipe.getRecipeId());
        productRecipeService.saveProductRecipe(productRecipe, recipe);
        return "redirect:/recipes/" + recipeId + "/products";
    }

    @GetMapping(value = "/{recipeId}/{productId}")
    public String removeProductFromRecipe(@PathVariable Long productId, @PathVariable Long recipeId) throws Exception {
        productRecipeService.deleteProductRecipe(productId);
        return "redirect:/recipes/" + recipeId + "/products";
    }

}