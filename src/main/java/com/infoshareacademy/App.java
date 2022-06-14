package com.infoshareacademy;

import com.infoshareacademy.fridge.Fridge;
import com.infoshareacademy.fridge.FridgeMethods;
import com.infoshareacademy.recipe.Recipe;
import com.infoshareacademy.recipe.RecipeMethods;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/*        Recipe recipe = new Recipe();
        recipe.setName("herbata");
        recipe.setDescription("pyszna");
        Map<String,Double> map = new HashMap<>();
        map.put("woda",300.0);
        map.put("herbata", 1.0);
        recipe.setNeccesaryProducts(map);
        recipe.setPreparationTime(15);

        Recipe recipe2 = new Recipe();
        recipe2.setName("herbata2");
        recipe2.setDescription("pyszna2");
        Map<String,Double> map2 = new HashMap<>();
        map2.put("woda2",2300.0);
        map2.put("herbata2", 1.20);
        recipe2.setNeccesaryProducts(map);
        recipe2.setPreparationTime(15);*/

        RecipeMethods recipeMethods = new RecipeMethods();
        FridgeMethods fridgeMethods = new FridgeMethods();

        try {
            List<Recipe> recipe = recipeMethods.getJson();
            List<Fridge> fridge = fridgeMethods.getJson();
/*            recipeMethods.showAll(recipe);*/
/*            recipeMethods.findRecipeByName(recipe);*/
            recipeMethods.findRecipeByProducts(recipe,fridge);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
