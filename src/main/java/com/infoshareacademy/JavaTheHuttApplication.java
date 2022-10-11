package com.infoshareacademy;


import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeAllergensRepository;
import com.infoshareacademy.repository.RecipeRepository;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JavaTheHuttApplication implements CommandLineRunner {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeAllergensRepository allergensRepository;

    @Autowired
    private FridgeService fridgeService;

    public static void main(String[] args) {
        SpringApplication.run(JavaTheHuttApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {

        List<Recipe> allRecipe = recipeService.getRecipeByProductsInFridge();

        for(int i=0;i<allRecipe.size();i++){
            System.out.println(allRecipe.get(i).toString());
        }


    }
}
