package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.ProductRecipeDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{recipeId}/products")
    public String editProductRecipeList(@PathVariable Long recipeId, Model model) {
        List<ProductRecipeDto> productRecipeList = productService.getAllProductByRecipeId(recipeId);
        model.addAttribute("productList", productRecipeList);
        model.addAttribute("productRecipe", productRecipeList.stream().findFirst().get());
        return "edit-product-recipe";
    }

    @PostMapping("/{recipeId}/products")
    public String saveProductRecipe(@PathVariable Long recipeId, @ModelAttribute RecipeDto.ProductRecipeDto productRecipe, @ModelAttribute("recipe") RecipeDto recipe) {
        productService.saveProductRecipe(productRecipe, recipe);
        return "redirect:/recipes/" + recipeId + "/products";
    }

    @GetMapping("/{recipeId}/products/{productId}")
    public String editProductRecipeForm(@PathVariable Long productId, Model model) throws Exception {
        model.addAttribute("productRecipe", productService.findById(productId));
        return "edit-product-recipe-form";
    }

    @PostMapping("/{recipeId}/products/{productId}")
    public String editProductRecipeForm(@PathVariable Long recipeId, @ModelAttribute RecipeDto.ProductRecipeDto productRecipe,@ModelAttribute("recipe") RecipeDto recipe) {
        productService.saveProductRecipe(productRecipe, recipe);
        return "redirect:/recipes/" + recipeId + "/products";
    }

    @GetMapping(value = "/{recipeId}/{productId}")
    public String removeUpdProduct(@PathVariable Long productId, @PathVariable Long recipeId) throws Exception {
        productService.deleteProductRecipe(productId);
        return "redirect:/recipes/" + recipeId + "/products";
    }

}