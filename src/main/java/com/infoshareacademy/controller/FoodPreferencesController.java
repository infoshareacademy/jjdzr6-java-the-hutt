package com.infoshareacademy.controller;

import com.infoshareacademy.entity.food_preferences.AllergenName;
import com.infoshareacademy.entity.food_preferences.Meat;
import com.infoshareacademy.service.FoodPreferencesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FoodPreferencesController {

    private FoodPreferencesService foodPreferencesService;

    public FoodPreferencesController(FoodPreferencesService foodPreferencesService) {
        this.foodPreferencesService = foodPreferencesService;
    }

    @GetMapping("/foodpreferences/{id}")
    public String getAllergenName(Model model, @PathVariable Long id) {
        model.addAttribute("foodpreferences", foodPreferencesService.getAllergenName(id));
        return "foodpreferences";
    }

/*    @GetMapping("/foodpreferences/{id}")
    public String getMeat(Model model, @PathVariable Long id) {
        model.addAttribute("foodpreferences", foodPreferencesService.getMeat(id));
        return "foodpreferences";
    }*/

    @GetMapping("/foodpreferences/edit/{id}")
    public String editAllergenForm(@PathVariable Long id, Model model) {
        model.addAttribute("foodpreferences", foodPreferencesService.getAllergenName(id));
        return "edit_foodpreferences";
    }

/*    @GetMapping("/foodpreferences/edit/{id}")
    public String editMeatForm(@PathVariable Long id, Model model) {
        model.addAttribute("foodpreferences", foodPreferencesService.getMeat(id));
        return "edit_foodpreferences";
    }*/
    @PostMapping("/foodpreferences/{id}")
    public String updateAllergenName(@PathVariable Long id,
                                     @ModelAttribute("foodpreferences") AllergenName allergenName,
                                     Model model) {

        AllergenName existingAlergens = foodPreferencesService.getAllergenName(id);
        existingAlergens.setId(allergenName.getId());
        existingAlergens.setDairy(allergenName.isDairy());
        existingAlergens.setChocolate(allergenName.isChocolate());
        existingAlergens.setEggs(allergenName.isEggs());
        existingAlergens.setNuts(allergenName.isNuts());
        existingAlergens.setShellfish(allergenName.isShellfish());
        existingAlergens.setStrawberries(allergenName.isStrawberries());
        existingAlergens.setOther(allergenName.getOther());

        foodPreferencesService.setAllergenPreferences(existingAlergens);
        return "redirect:/foodpreferences";
    }

/*    @PostMapping("/foodpreferences/{id}")
    public String updateMeat(@PathVariable Long id,
                             @ModelAttribute("foodpreferences") Meat meat,
                             Model model) {


        Meat existingMeat = foodPreferencesService.getMeat(id);
        existingMeat.setId(meat.getId());
        existingMeat.setMeatEater(meat.isMeatEater());
        existingMeat.setVegan(meat.isVegan());
        existingMeat.setVegetarian(meat.isVegetarian());

        foodPreferencesService.setMeatPreferences(existingMeat);
        return "redirect:/foodpreferences";
    }*/

}
