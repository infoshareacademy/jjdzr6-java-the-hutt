package com.infoshareacademy;

import com.infoshareacademy.service.RecipeService;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        System.out.println("Team name: Java The Hutt");

/*        FridgeMethods fridgeMethods = new FridgeMethods();

       try{
           fridgeMethods.getJson();
       } catch (IOException e) {
           System.out.println("IO");
        }
    }*/

        RecipeService recipeService = new RecipeService();
        try {
            recipeService.getJson();
        } catch (IOException e) {
            System.out.println("IO");
        }
    }
}
