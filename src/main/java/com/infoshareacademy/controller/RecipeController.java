package com.infoshareacademy.controller;


import com.infoshareacademy.entity.product.Product;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import com.infoshareacademy.service.ProductService;
import com.infoshareacademy.service.RecipeService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("recipes")
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
    private ProductService productService;
    private RecipeRepository recipeRepository;

    public RecipeController(RecipeService recipeService, ProductService productService, RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.productService = productService;
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
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }


    @PostMapping(value = "/new", params = {"addProduct"})
    public String addProduct(@ModelAttribute("recipe") Recipe recipe) {
        recipe.addProduct(new Product());
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
        existingRecipe.setProductList(recipe.getProductList());

        recipeService.saveRecipe(existingRecipe);
        return "redirect:/recipes";
    }

    //TODO: produkty update i remove
    @PostMapping(value = "/edit/{id}", params = {"addEditProduct"})
    public String addUpdProduct(@ModelAttribute("recipe") Recipe recipe, @PathVariable Long id) {
        recipe = recipeService.getRecipeById(id);
        //TODO
        recipe.addProduct(new Product());
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
