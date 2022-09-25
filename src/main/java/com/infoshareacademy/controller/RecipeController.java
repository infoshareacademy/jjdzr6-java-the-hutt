package com.infoshareacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.infoshareacademy.repository.RecipeRepository;
import com.infoshareacademy.service.ProductService;
import com.infoshareacademy.service.RecipeService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;
    private final ProductService productService;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeRepository recipeRepository,
                            final ProductService productService) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
        this.productService = productService;
    }

    @GetMapping
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipe());
        return "recipes";
    }

    @GetMapping("/filtered-prodcts")
    public String searchRecipeList(Model model, @Param("keyword") String keyword) {
        model.addAttribute("recipes", recipeService.getSearchRecipe(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("recipeAsc", recipeService.sortByNameAsc());
        return "search-recipe";
    }

    @GetMapping("/recipe")
    public String createRecipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "create-recipe";
    }

    @PostMapping("/recipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-recipe";
        }
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @PostMapping(value = "/recipe", params = {"addProduct"})
    public String addProduct(@ModelAttribute Recipe recipe) {
        recipe.addProduct(new ProductRecipe());
        return "create-recipe";
    }

    @PostMapping(value = "/recipe", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute Recipe recipe, HttpServletRequest request) {
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
    public String updateRecipe(@PathVariable Long recipeId, @ModelAttribute Recipe recipe) {
        recipeService.updateRecipe(recipeId, recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/recipe/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }

    @GetMapping("recipe-asc")
    public String sortByNameAsc(Model model){
        model.addAttribute("recipeAsc", recipeService.sortByNameAsc());
        return "recipe-asc";
    }

    @GetMapping("recipe-desc")
    public String sortByNameDesc(Model model){
        model.addAttribute("recipeDesc", recipeService.sortByNameDesc());
        return "recipe-desc";
    }
}