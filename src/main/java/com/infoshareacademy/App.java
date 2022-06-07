package com.infoshareacademy;

import com.infoshareacademy.fridge.Fridge;
import com.infoshareacademy.fridge.FridgeMethods;
import com.infoshareacademy.recipe.RecipeMethods;

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

        RecipeMethods recipeMethods = new RecipeMethods();
        try {
            recipeMethods.getJson();
        } catch (IOException e) {
            System.out.println("IO");
        }
    }
}
