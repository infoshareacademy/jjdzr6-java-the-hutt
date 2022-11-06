package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllegrens;
import com.infoshareacademy.repository.RecipeAllergensRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.infoshareacademy.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeAllergensRepository allergensRepository;

    private final FridgeService fridgeService;

    private static Logger LOGGER = LogManager.getLogger(RecipeService.class.getName());

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeAllergensRepository allergensRepository, FridgeService fridgeService) {
        this.recipeRepository = recipeRepository;
        this.allergensRepository = allergensRepository;
        this.fridgeService = fridgeService;
    }

    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    public Page<Recipe> getSearchRecipe(String keyword, Pageable pageable) {
        if (keyword != null) {
            return new PageImpl<>(recipeRepository.findRecipeBy(keyword));
        }
        return recipeRepository.findAll(pageable);
    }

    public Recipe getRecipeById(Long id) {
        Recipe recipe = new Recipe();
        if (recipeRepository.findById(id).isPresent()) recipe = recipeRepository.findById(id).get();
        return recipe;
    }

    public Page<Recipe> getRecipesByCanFilterByMeal(Meal meal, Pageable pageable) {
        if (meal != null) {
            List<Recipe> recipeList = recipeRepository.findAll();

            switch (meal) {

                case BREAKFAST:
                    recipeList = recipeList.stream()
                            .filter(recipe -> recipe.getMeal().equals(Meal.BREAKFAST))
                            .toList();
                    break;
                case LUNCH:
                    recipeList = recipeList.stream()
                            .filter(recipe -> recipe.getMeal().equals(Meal.LUNCH))
                            .toList();
                    break;
                case SUPPER:
                    recipeList = recipeList.stream()
                            .filter(recipe -> recipe.getMeal().equals(Meal.SUPPER))
                            .toList();
                    break;
                case DINNER:
                    recipeList = recipeList.stream()
                            .filter(recipe -> recipe.getMeal().equals(Meal.DINNER))
                            .toList();
                    break;
                case ALL:
                    recipeList = recipeRepository.findAll();
                    break;
            }

            return new PageImpl<>(recipeList);
        } else return recipeRepository.findAll(pageable);
    }

    public void saveRecipeAllergens(Long id, RecipeAllegrens allergens) {
        Recipe recipe = new Recipe();
        recipe.setUserId(fridgeService.getDEFAULT_FRIDGE_ID());
        if (recipeRepository.findById(id).isPresent()) recipe = recipeRepository.findById(id).get();
        RecipeAllegrens existingAllergens = new RecipeAllegrens();
        if (allergensRepository.findById(recipe.getRecipeAllegrens().getId()).isPresent()) {
            existingAllergens = allergensRepository.findById(recipe.getRecipeAllegrens().getId()).get();
            existingAllergens.setRecipe(recipe);
            existingAllergens.setChocolate(allergens.isChocolate());
            existingAllergens.setDairy(allergens.isDairy());
            existingAllergens.setEggs(allergens.isEggs());
            existingAllergens.setMeatEater(allergens.isMeatEater());
            existingAllergens.setNuts(allergens.isNuts());
            existingAllergens.setOther(allergens.getOther());
            existingAllergens.setShellfish(allergens.isShellfish());
            existingAllergens.setStrawberries(allergens.isStrawberries());
            existingAllergens.setVegan(allergens.isVegan());
            existingAllergens.setVegetarian(allergens.isVegetarian());
            allergensRepository.save(existingAllergens);
            LOGGER.info("Zapisano preferencje żywieniowe!");

        }
    }

    public Recipe saveRecipe(Recipe recipe) {
        recipe.getProductList().forEach(x -> x.setRecipe(recipe));
        recipe.getRecipeAllegrens().setRecipe(recipe);
        recipe.setUserId(fridgeService.getDEFAULT_FRIDGE_ID());
        LOGGER.info("Zapisano przepis: " + recipe.getName());
        return recipeRepository.save(recipe);


    }


    public void updateRecipe(Long recipeId, Recipe recipe) {

        Recipe existingRecipe = new Recipe();
        existingRecipe.setUserId(fridgeService.getDEFAULT_FRIDGE_ID());
        if (recipeRepository.findById(recipeId).isPresent()) existingRecipe = recipeRepository.findById(recipeId).get();
        existingRecipe.setRecipeId(recipeId);
        existingRecipe.setName(recipe.getName());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setPreparationTime(recipe.getPreparationTime());
        existingRecipe.setRecipeAllegrens(recipe.getRecipeAllegrens());
        existingRecipe.setMeal(recipe.getMeal());
        recipeRepository.save(existingRecipe);
        LOGGER.info("Zaktualizowano przepis: " + recipe.getName());
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteByRecipeId(id);
    }

    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    public List<Recipe> getRecipesWithProductsToLowerCase() {
        List<Recipe> recipes = getAllRecipe().stream()
                .peek(recipe -> recipe.getProductList()
                        .forEach(productRecipe -> productRecipe
                                .setProductName(productRecipe.getProductName().toLowerCase()))).toList();
        return recipes;
    }

    public Map<Recipe, List<ProductRecipe>> mapRecipeProducts(){
        Map<Recipe, List<ProductRecipe>> mapRecipesProducts = getRecipesWithProductsToLowerCase().stream()
                .collect(Collectors.toMap(Function.identity(), Recipe::getProductList));
        return mapRecipesProducts;
    }

    public Page<Recipe> getRecipeByProductsInFridge(Pageable pageable) {

        //TODO: zmienić wczytywanie przepisów po ID
        List<Recipe> myRecipes = getRecipesWithProductsToLowerCase();

        Map<String, ProductInFridge> productsInFridge = fridgeService.mapProductsInFridgeWithNameAsKey();
        List<Recipe> filteredRecipies = new ArrayList<>();


        Map<Recipe, List<ProductRecipe>> mapRecipesProducts = mapRecipeProducts();

        for (Map.Entry<Recipe, List<ProductRecipe>> entry : mapRecipesProducts.entrySet()) {
            List<ProductRecipe> tempRecipeList = entry.getValue();
            int matchScore = tempRecipeList.size();
            for (ProductRecipe productRecipe : tempRecipeList) {
                ProductInFridge productInFridge = productsInFridge.get(productRecipe.getProductName());

                if (productInFridge != null
                        && productInFridge.getAmount() >= productRecipe.getAmount()) {
                    matchScore--;
                }
            }
            if (matchScore == 0) {
                filteredRecipies.add(entry.getKey());
            }
        }

        return new PageImpl<>(filteredRecipies);
    }

}