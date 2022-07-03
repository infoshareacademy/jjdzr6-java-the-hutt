package com.infoshareacademy;

import com.infoshareacademy.service.RecipeService;
import com.infoshareacademy.service.ShoppingListService;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Team name: Java The Hutt");


        ShoppingListService shoppingListService = new ShoppingListService();
        shoppingListService.createShoppingList();

    }
}
