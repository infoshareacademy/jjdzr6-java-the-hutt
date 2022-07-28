package com.infoshareacademy;

import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JavaTheHuttApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(JavaTheHuttApplication.class, args);


//		RecipeService recipeService = new RecipeService();
//		List<Recipe> recipe = new ArrayList<>();
//		recipe.addAll(recipeService.getJson());
//        System.out.println(recipe.get(2));
//        System.out.println("-----------");
//        System.out.println(recipe);
	}

}
