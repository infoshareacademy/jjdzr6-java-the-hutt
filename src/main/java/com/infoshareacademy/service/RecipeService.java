package com.infoshareacademy.service;


import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.RecipeAllergensDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import com.infoshareacademy.repository.RecipeAllergensRepository;
import com.infoshareacademy.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeAllergensRepository allergensRepository;

    private final FridgeService fridgeService;

    private final ModelMapper modelMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeAllergensRepository allergensRepository, FridgeService fridgeService, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.allergensRepository = allergensRepository;
        this.fridgeService = fridgeService;
        this.modelMapper = modelMapper;
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll().stream().map(recipeDto -> modelMapper.map(recipeDto, RecipeDto.class)).toList();
    }

    @Transactional
    public void setUserIdForInitRecipes() {
        recipeRepository.findAll().forEach(recipe -> recipe.setUserId(fridgeService.getUserId()));
    }

    public Page<RecipeDto> getSearchedRecipe(String keyword, Pageable pageable) {
        Page<Recipe> pageRecipes = recipeRepository.findAll(pageable);
        if (keyword != null) {
            pageRecipes = recipeRepository.findRecipeBy(keyword, pageable);
        }
        return pageRecipes.map(recipe -> modelMapper.map(recipe, RecipeDto.class));

    }

    public RecipeDto getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        return modelMapper.map(recipe, RecipeDto.class);
    }

    @Transactional
    public Page<RecipeDto> getRecipesFilteredByMeal(Meal meal, Pageable pageable) {
        Page<Recipe> pageRecipes = recipeRepository.findAll(pageable);
        if (meal != null && meal != Meal.ALL) {
            pageRecipes = recipeRepository.findRecipeByMeal(meal, pageable);
        }
        return pageRecipes.map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }

    public void saveRecipeAllergens(Long id, RecipeAllergensDto allergens) {
        Recipe recipe;
        recipe = recipeRepository.findById(id).orElse(new Recipe());
        recipe.setUserId(fridgeService.getUserId());
        RecipeAllergens existingAllergens;
        existingAllergens = allergensRepository.findById(recipe.getRecipeAllergens().getId()).orElse(new RecipeAllergens());
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
        log.info("Zapisano preferencje żywieniowe!");
    }

    @Transactional
    public void saveRecipe(RecipeDto recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        recipe.getProductList().forEach(x -> x.setRecipe(recipe));
        recipe.getProductList().forEach(this::convertUnitsInProducts);
        recipe.getRecipeAllergens().setRecipe(recipe);
        recipe.setUserId(fridgeService.getUserId());
        log.info("Zapisano przepis: " + recipe.getName());
        recipeRepository.save(recipe);
    }


    public void updateRecipe(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        Recipe existingRecipe = recipeRepository.findById(recipeId).orElse(new Recipe());
        existingRecipe.setUserId(fridgeService.getUserId());
        existingRecipe.setRecipeId(recipeId);
        existingRecipe.setName(recipe.getName());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setPreparationTime(recipe.getPreparationTime());
        existingRecipe.setMeal(recipe.getMeal());
        existingRecipe.getProductList().forEach(this::convertUnitsInProducts);
        recipeRepository.save(existingRecipe);
        log.info("Zaktualizowano przepis: " + recipe.getName());
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteByRecipeId(id);
        log.info("Usunięto przepis o id: " + id);

    }

    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
        log.info("Usunięto wszystkie przpeisy");
    }

    public List<RecipeDto> getRecipesWithProductsToLowerCase() {
        return getAllRecipes().stream()
                .peek(recipe -> recipe.getProductList()
                        .forEach(productRecipe -> productRecipe
                                .setProductName(productRecipe.getProductName().toLowerCase()))).toList();
    }

    public Map<RecipeDto, List<RecipeDto.ProductRecipeDto>> mapRecipesWithProducts() {
        return getRecipesWithProductsToLowerCase().stream()
                .collect(Collectors.toMap(Function.identity(), RecipeDto::getProductList));
    }

    public Page<RecipeDto> getRecipesFilteredByProductsInFridge(Pageable pageable) {

        Map<String, FridgeDto.ProductInFridgeDto> productsInFridge = fridgeService.mapProductsInFridgeWithNameAsKey();
        List<RecipeDto> filteredRecipies = new ArrayList<>();


        for (Map.Entry<RecipeDto, List<RecipeDto.ProductRecipeDto>> entry : mapRecipesWithProducts().entrySet()) {
            List<RecipeDto.ProductRecipeDto> tempRecipeList = entry.getValue();
            int matchScore = tempRecipeList.size();

            for (RecipeDto.ProductRecipeDto productRecipe : tempRecipeList) {
                FridgeDto.ProductInFridgeDto productInFridge = productsInFridge.get(productRecipe.getProductName());

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

    public void convertUnitsInProducts(ProductRecipe product) {
        double convertedAmount;
        switch (product.getUnit()) {
            case MILIGRAM -> {
                convertedAmount = (product.getAmount()) / 1000;
                product.setAmount(convertedAmount);
                product.setUnit(ProductUnit.GRAM);
            }
            case KILOGRAM -> {
                convertedAmount = (product.getAmount()) * 1000;
                product.setAmount(convertedAmount);
                product.setUnit(ProductUnit.GRAM);
            }
            case LITR -> {
                convertedAmount = (product.getAmount()) * 1000;
                product.setAmount(convertedAmount);
                product.setUnit(ProductUnit.MILILITR);
            }
        }
    }

}