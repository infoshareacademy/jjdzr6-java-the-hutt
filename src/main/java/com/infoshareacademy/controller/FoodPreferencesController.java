package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.FoodPreferencesDto;
import com.infoshareacademy.service.FoodPreferencesService;
import com.infoshareacademy.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "food-preferences";
    }

    @GetMapping("/foodpreferences/set-foodpreferences")
    public String createFoodPreferencesForm(Model model, Optional<FoodPreferencesDto> foodPreferencesDto) {
        model.addAttribute("foodpreferences", foodPreferencesService.checkIfFoodPreferencesIsSet(foodPreferencesDto));
        return "set-food-preferences";
    }

    @PostMapping("/foodpreferences")
    public String saveFoodPreferences(@ModelAttribute("foodpreferences") FoodPreferencesDto foodPreferencesDto) {
        foodPreferencesService.setFoodPreferences(foodPreferencesDto);
        return "redirect:/foodpreferences";
    }

}