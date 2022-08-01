package com.infoshareacademy;

import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JavaTheHuttApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(JavaTheHuttApplication.class, args);

//		RecipeService recipeService = new RecipeService();
//		List<Recipe> recipe = new ArrayList<>();
//		recipe.addAll(recipeService.getJson());
//        System.out.println(recipe.get(2));
//        System.out.println("-----------");
//        System.out.println(recipe);
    }

    @Autowired
    private RecipeRepository repository;

    @Override
    public void run(String... args) {
        Recipe  tostyRecipe = new Recipe();
        tostyRecipe.addNecessaryProducts("herbata", 2.0);
        tostyRecipe.addNecessaryProducts("woda", 1.0);

        tostyRecipe.setName("herbata");
        tostyRecipe.setDescription("herbata czarna");
        tostyRecipe.setPreparationTime(3);
//        Recipe recipe1 = new Recipe("tosty", "Pyszne tosty z szynką I serem", 15, tostyRecipe.getNeccesaryProducts());
        System.out.println(tostyRecipe);

        repository.save(tostyRecipe);


    }
}
