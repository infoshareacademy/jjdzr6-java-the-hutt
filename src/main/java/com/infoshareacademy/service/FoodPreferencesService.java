package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FoodPreferencesDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import com.infoshareacademy.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class FoodPreferencesService {

    private final FoodPreferencesRepository foodPreferencesRepository;

    private final RecipeRepository recipeRepository;

    private final FridgeService fridgeService;

    private final ModelMapper modelMapper;

    @Autowired
    public FoodPreferencesService(FoodPreferencesRepository foodPreferencesRepository, RecipeRepository recipeRepository, FridgeService fridgeService, ModelMapper modelMapper) {
        this.foodPreferencesRepository = foodPreferencesRepository;
        this.recipeRepository = recipeRepository;
        this.fridgeService = fridgeService;
        this.modelMapper = modelMapper;
    }

    public Optional<FoodPreferencesDto> getFoodPreferencesById() {
        Long id = fridgeService.getUserId();
        return foodPreferencesRepository.findById(id).map(foodPreferences -> modelMapper.map(foodPreferences, FoodPreferencesDto.class));
    }

    public void setFoodPreferences(FoodPreferencesDto foodPreferencesDto) {
        foodPreferencesDto.setId(fridgeService.getUserId());
        FoodPreferences foodPreferences = modelMapper.map(foodPreferencesDto, FoodPreferences.class);
        foodPreferencesRepository.save(foodPreferences);
        log.info("Zapisano preferencje żywieniowe dla użytkownika o id: " + fridgeService.getUserId());
    }

    public Optional<FoodPreferencesDto> checkIfFoodPreferencesIsSet() {

        FoodPreferences foodPreferences = foodPreferencesRepository.findById(fridgeService.getUserId()).orElse(new FoodPreferences());
        return Optional.of(modelMapper.map(foodPreferences, FoodPreferencesDto.class));

    }

    public Page<RecipeDto> filterRecipeByFoodPreferences(Pageable pageable) {

        Optional<FoodPreferencesDto> foodPreferencesRepositoryDtoById = getFoodPreferencesById();
        Page<RecipeDto> recipePage = recipeRepository.findAll(pageable).map(recipe -> modelMapper.map(recipe, RecipeDto.class));
        List<Recipe> recipeList = recipeRepository.findAll();


        if (foodPreferencesRepositoryDtoById.isPresent()) {
            FoodPreferencesDto foodPreferencesDto = foodPreferencesRepositoryDtoById.get();
            if (foodPreferencesDto.isChocolate()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().isChocolate())
                        .toList();
            }
            if (foodPreferencesDto.isNuts()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().isNuts())
                        .toList();
            }
            if (foodPreferencesDto.isEggs()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().isEggs())
                        .toList();
            }
            if (foodPreferencesDto.isStrawberries()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().isStrawberries())
                        .toList();
            }
            if (foodPreferencesDto.isShellfish()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().isShellfish())
                        .toList();
            }

            if (foodPreferencesDto.isDairy()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().isDairy())
                        .toList();
            }
            if (isNotEqual("-", foodPreferencesDto.getOther())) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().getOther().equals("-"))
                        .toList();
            } else if (isNotEqual("brak", foodPreferencesDto.getOther())) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllergens().getOther().equals("brak"))
                        .toList();
            }
            if (foodPreferencesDto.isMeatEater()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllergens().isMeatEater())
                        .toList();
            } else if (foodPreferencesDto.isVegetarian()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllergens().isVegetarian())
                        .toList();

            } else if (foodPreferencesDto.isVegan()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllergens().isVegan())
                        .toList();
            }
            recipePage = createPageFromList(recipeList, pageable).map(recipe -> modelMapper.map(recipe, RecipeDto.class));
        }
        return recipePage;

    }

    static Page<Recipe> createPageFromList(List<Recipe> list, Pageable pageable) {
        if (list == null) {
            throw new IllegalArgumentException("To create a Page, the list mustn't be null!");
        }

        int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
        if (startOfPage > list.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        int endOfPage = Math.min(startOfPage + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(startOfPage, endOfPage), pageable, list.size());
    }


    private static boolean isNotEqual(String string1, String string2) {
        return !StringUtils.equalsIgnoreCase(string1, string2);
    }
}

