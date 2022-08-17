package com.infoshareacademy.controller;


import com.infoshareacademy.entity.product.Product;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("recipes/new")
    public String createRecipeForm(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "create_recipe";
    }

    @PostMapping("recipes")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    //-------------------------------------------
    @PostMapping(value = "recipes/new", params = {"addProduct"})
    public String addProduct(@ModelAttribute("recipe") Recipe recipe) {
        recipe.addProduct(new Product());
        return "create_recipe";
    }

    @PostMapping(value = "recipes/new", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute("recipe") Recipe recipe,
                                HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removeProduct"));
        recipe.getProductList().remove(index);
        return "create_recipe";
    }
    //-------------------------------------------

    @GetMapping("/recipes/edit/{id}")
    public String editRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "edit_recipe";
    }


    @PostMapping("/recipes/{id}")
    public String updateRecipe(@PathVariable Long id, @ModelAttribute("recipe") Recipe recipe, Model model) {

        Recipe existingRecipe = recipeService.getRecipeById(id);
        existingRecipe.setId(id);
        existingRecipe.setName(recipe.getName());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setPreparationTime(recipe.getPreparationTime());
        existingRecipe.setNeccesaryProducts(recipe.getNeccesaryProducts());

        recipeService.saveRecipe(existingRecipe);

        return "redirect:/recipes";
    }

    @GetMapping("/recipes/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }
}

