package com.infoshareacademy;

import com.infoshareacademy.entity.product.Product;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import com.infoshareacademy.service.ProductService;
import com.infoshareacademy.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

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
    private RecipeRepository recipeRepository;

    @Override
    public void run(String... args) {
//        Recipe tostyRecipe = new Recipe();
//        tostyRecipe.addNecessaryProducts("jablka", 1.0);
//        tostyRecipe.addNecessaryProducts("mleko", 1.0);
//
//
//        tostyRecipe.setName("jablka z mlekiem");
//        tostyRecipe.setDescription("jablka");
//        tostyRecipe.setPreparationTime(10);
//
//        System.out.println(tostyRecipe);
//
//        recipeRepository.save(tostyRecipe);

//        RecipeService recipeService = new RecipeService(recipeRepository);
//        System.out.println(recipeService.getAllRecipe());
//
//        Recipe recipe = new Recipe();
//        recipe.addProduct(new Product("hej", 2.0));
//        recipe.addProduct(new Product("ho", 2.0));
//        recipe.addProduct(new Product("hi", 2.0));
//
//
//        recipe.setId(40L);
//        recipe.setName("HAHA");
//        recipe.setDescription("huhuhu");
//        recipe.setPreparationTime(3);
//        System.out.println(recipe);
//
//        recipeService.saveRecipe(recipe);
    }
}
