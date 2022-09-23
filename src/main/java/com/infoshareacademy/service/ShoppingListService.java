package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    @Autowired
    private FridgeService fridgeService;
    @Autowired
    private RecipeService recipeService;


    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }


    public List<ProductShoppingList> createShoppingList() {


        List<Recipe> recipeList = recipeService.getAllRecipe();
        List<ProductInFridge> productsInFridge = fridgeService.getAllProductsFromFridge().getProductsInFridge();

        Map<String, Double> fridgeMap = productsInFridge.stream().collect(Collectors.toMap(ProductInFridge::getProductName, ProductInFridge::getAmount));

        for (String key : fridgeMap.keySet()) {
            if (fridgeMap.get(key) == null) {
                fridgeMap.putIfAbsent(key, fridgeMap.get(key));
            } else {
                Double value = fridgeMap.get(key) + fridgeMap.get(key);
                fridgeMap.put(key, value);
            }
        }

        Map<String, Double> recipeMap = new HashMap<>();
        List<ProductShoppingList> shoppingList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            Map<String, Double> recipeOne = recipe.getProductList().stream().collect(Collectors.toMap(ProductRecipe::getProductName, ProductRecipe::getAmount));

            for (String key : recipeOne.keySet()) {
                if (recipeMap.get(key) == null) {
                    recipeMap.putIfAbsent(key, recipeOne.get(key));
                } else {
                    Double value = recipeMap.get(key) + recipeOne.get(key);
                    recipeMap.put(key, value);
                }
            }
            Map<String, Double> map = hashMapDifference(fridgeMap, recipeMap);


            for (String key : map.keySet()) {
                ProductShoppingList product = new ProductShoppingList();
                product.setProductName(key);
                product.setAmount(map.get(key));
                shoppingList.add(product);

            }


        }

        List<ProductShoppingList> productShoppingLists = shoppingList.stream().distinct().toList();
        return productShoppingLists;


    }

    public Map<String, Double> hashMapDifference(Map<String, Double> a, Map<String, Double> b) {
        Iterator<String> bKeyIterator = b.keySet().iterator();
        String key;
        Double value;
        Map<String, Double> difference = new HashMap<>();

        while (bKeyIterator.hasNext()) {
            key = bKeyIterator.next();
            if (a.get(key) == null) {
                value = b.get(key);
            } else {
                value = b.get(key) - a.get(key);
            }
            if (value > 0) {
                difference.put(key, value);
            }
        }
        return difference;
    }


}