package com.infoshareacademy.service;

import com.infoshareacademy.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipeServiceTest {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeService recipeService;

    @Test
    void sortByPreparationTimeAsc() {
        System.out.println(recipeService.sortByPreparationTimeAsc());
    }

    @Test
    void sortByPreparationTimeDesc() {
        System.out.println(recipeService.sortByPreparationTimeDesc());
    }
}