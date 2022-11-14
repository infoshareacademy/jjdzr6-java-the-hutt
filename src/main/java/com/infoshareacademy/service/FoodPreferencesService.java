package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FoodPreferencesDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import com.infoshareacademy.repository.RecipeRepository;
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

    private final RecipeRepository recipeRepository;

    private final FridgeService fridgeService;

    private final ModelMapper modelMapper;

    @Autowired
    public FoodPreferencesService(FoodPreferencesRepository foodPreferencesRepository, RecipeService recipeService, RecipeRepository recipeRepository, FridgeService fridgeService, ModelMapper modelMapper) {
        this.foodPreferencesRepository = foodPreferencesRepository;
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
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

    // TODO: przerobic na query
    public Page<Recipe> filterRecipeByFoodPreferences(Long id, Pageable pageable) {

        Optional<FoodPreferencesDto> foodPreferencesRepositoryDtoById = getFoodPreferencesById(id);
        List<Recipe> recipeList = recipeRepository.findAll();

        Page<Recipe> recipePage = recipeRepository.findAll(pageable);

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
        recipePage = new PageImpl<>(recipeList);
        }
        return recipePage;

    }

    private static boolean isNotEqual(String string1, String string2) {
        return !StringUtils.equalsIgnoreCase(string1, string2);
    }
}

