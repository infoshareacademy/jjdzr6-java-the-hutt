package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.FoodPreferencesDto;
import com.infoshareacademy.service.FoodPreferencesService;
import com.infoshareacademy.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class FoodPreferencesController {

    private final FoodPreferencesService foodPreferencesService;

    private final FridgeService fridgeService;

    @Autowired
    public FoodPreferencesController(FoodPreferencesService foodPreferencesService, FridgeService fridgeService) {
        this.foodPreferencesService = foodPreferencesService;
        this.fridgeService = fridgeService;
    }

    @GetMapping("/foodpreferences")
    public String listFoodPreferences(Model model) {
        model.addAttribute("getfoodpreferences", foodPreferencesService.getFoodPreferences());
        return "foodpreferences";
    }

    @GetMapping("/foodpreferences/{id}")
    public String getFoodPreferencesById(@PathVariable Long id, Model model) {
        model.addAttribute("foodpreferencesbyid", foodPreferencesService.getFoodPreferencesById(id));
        return "foodpreferencesbyid";
    }

    @GetMapping("/foodpreferences/recipe/")
    public String getRecipeByAllergens(Model model, @SortDefault(value = "name") @PageableDefault(size = 3) Pageable pageable) {
        model.addAttribute("recipes", foodPreferencesService.filterRecipeByFoodPreferences(fridgeService.getDEFAULT_FRIDGE_ID(), pageable));
        return "recipes";
    }

    @GetMapping("/foodpreferences/user-foodpreferences")
    public String createAllergensForm(Model model, Optional<FoodPreferencesDto> foodPreferencesDto) {
        model.addAttribute("foodpreferences", foodPreferencesService.checkIfFoodPreferencesIsSet(foodPreferencesDto));
        return "setfoodpreferences";

    }

    @PostMapping("/foodpreferences")
    public String saveAllergens(@ModelAttribute("foodpreferences") FoodPreferencesDto foodPreferencesDto) {
        foodPreferencesService.setFoodPreferences(foodPreferencesDto);
        return "redirect:/foodpreferences";
    }


}