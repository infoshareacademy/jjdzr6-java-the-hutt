package com.infoshareacademy.controller;

import com.infoshareacademy.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ShoppingListController {
    private ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/shoppinglist")
    public String shoppingList(Model model) {
        model.addAttribute("getShopping", shoppingListService.createShoppingList());
        return "shoppinglist";
    }
}
