package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.ShoppingListDto;
import com.infoshareacademy.entity.shopping_list.ShoppingList;
import com.infoshareacademy.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/shopping-lists")
    public String shoppingList(Model model) {
        model.addAttribute("getShopping", shoppingListService.findAllShoppingLists());
        return "shoppinglist";
    }

    @GetMapping("/shopping-lists/shopping-list")
    public String createShoppingListForm(Model model) {
        model.addAttribute("shoppinglist", new ShoppingListDto());
        return "create-shopping-list";
    }

    @PostMapping("/shopping-lists/shopping-list")
    public String saveShoppingList(@Valid @ModelAttribute("shoppingList") ShoppingListDto shoppingList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-shopping-list";
        }
        shoppingListService.saveShoppingList(shoppingList);
        return "redirect:/shopping-lists";
    }

    @GetMapping("/shopping-lists/{shoppingListId}")
    public String viewShoppingList(@PathVariable Long shoppingListId, Model model) {
        model.addAttribute("shoppinglist", shoppingListService.viewShoppingList(shoppingListId));
        return "details-shopping-list";
    }


    @GetMapping("/shopping-lists/list/{shoppingListId}")
    public String deleteRecipe(@PathVariable Long shoppingListId) {
        shoppingListService.deleteShoppingListById(shoppingListId);
        return "redirect:/shopping-lists";
    }
}