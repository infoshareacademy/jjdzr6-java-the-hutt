package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FoodPreferencesDto;
import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FoodPreferencesService {


    private final FoodPreferencesRepository foodPreferencesRepository;

    private final RecipeService recipeService;

    private final FridgeService fridgeService;

    private final ModelMapper modelMapper;

    @Autowired
    public FoodPreferencesService(FoodPreferencesRepository foodPreferencesRepository, RecipeService recipeService, FridgeService fridgeService, ModelMapper modelMapper) {
        this.foodPreferencesRepository = foodPreferencesRepository;
        this.recipeService = recipeService;
        this.fridgeService = fridgeService;
        this.modelMapper = modelMapper;
    }

    public List<FoodPreferencesDto> getFoodPreferences() {
        return foodPreferencesRepository.findAll().stream().map(foodPreferences -> modelMapper.map(foodPreferences, FoodPreferencesDto.class)).collect(Collectors.toList());
    }

    public Optional<FoodPreferencesDto> getFoodPreferencesById(Long id) {
        return foodPreferencesRepository.findById(id).map(foodPreferences -> modelMapper.map(foodPreferences, FoodPreferencesDto.class));
    }

    public void setFoodPreferences(FoodPreferencesDto foodPreferencesDto) {
        foodPreferencesDto.setId(fridgeService.getDEFAULT_FRIDGE_ID());
        FoodPreferences foodPreferences = modelMapper.map(foodPreferencesDto, FoodPreferences.class);
        foodPreferencesRepository.save(foodPreferences);
    }

    public Optional<FoodPreferencesDto> checkIfFoodPreferencesIsSet(Optional<FoodPreferencesDto> foodPreferencesDto) {
        if (foodPreferencesRepository.findById(fridgeService.getDEFAULT_FRIDGE_ID()).isPresent()) {
            foodPreferencesDto = getFoodPreferencesById(fridgeService.getDEFAULT_FRIDGE_ID());
            return foodPreferencesDto;
        } else {
            return Optional.of(new FoodPreferencesDto());
        }
    }

    public Page<Recipe> filterRecipeByFoodPreferences(Long id, Pageable pageable) {

        Optional<FoodPreferencesDto> foodPreferencesRepositoryDtoById = getFoodPreferencesById(id);
        List<Recipe> recipeList = recipeService.getAllRecipe();

        if (foodPreferencesRepositoryDtoById.isPresent()) {
            FoodPreferencesDto foodPreferencesDto = foodPreferencesRepositoryDtoById.get();
            if (foodPreferencesDto.isChocolate()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isChocolate())
                        .toList();
            }
            if (foodPreferencesDto.isNuts()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isNuts())
                        .toList();
            }
            if (foodPreferencesDto.isEggs()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isEggs())
                        .toList();
            }
            if (foodPreferencesDto.isStrawberries()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isStrawberries())
                        .toList();
            }
            if (foodPreferencesDto.isShellfish()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isShellfish())
                        .toList();
            }

            if (foodPreferencesDto.isDairy()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isDairy())
                        .toList();
            }


            if (isNotEqual("-", foodPreferencesDto.getOther())) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().getOther().equals("-"))
                        .toList();
            } else if (isNotEqual("brak", foodPreferencesDto.getOther())) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().getOther().equals("brak"))
                        .toList();
            }
            if (foodPreferencesDto.isMeatEater()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isMeatEater())
                        .toList();
            } else if (foodPreferencesDto.isVegetarian()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isVegetarian())
                        .toList();

            } else if (foodPreferencesDto.isVegan()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isVegan())
                        .toList();
            }

        }
        return new PageImpl<>(recipeList);

    }

    private static boolean isNotEqual(String string1, String string2) {
        return !StringUtils.equalsIgnoreCase(string1, string2);
    }
}

