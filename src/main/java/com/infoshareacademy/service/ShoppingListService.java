package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.Product;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final FridgeService fridgeService = new FridgeService();
    private final RecipeService recipeService = new RecipeService();


    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }




    public List<Product> createShoppingList(){


        List<Recipe> recipeList = recipeService.getAllRecipe;
        List<Product> productsInFridge = fridgeService.getProductsFromFridge;

        Map<String, Double> fridgeMap = productsInFridge.stream().collect(Collectors.toMap(Product::getProductName, Product::getAmount));

            for (String key : fridgeMap.keySet()) {
                if (fridgeMap.get(key) == null) {
                    fridgeMap.putIfAbsent(key, fridgeMap.get(key));
                } else {
                    Double value = fridgeMap.get(key) + fridgeMap.get(key);
                    fridgeMap.put(key, value);
                }
            }

        Map<String, Double> recipeMap = new HashMap<>();
        List<Product> shoppingList = null;
        for (Recipe recipe : recipeList) {
            Map<String, Double> recipeOne = recipe.getNeccesaryProducts();

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
                Product product = new Product();
                if (shoppingList.get(shoppingList.indexOf(key)) == null) {
                    product.setProductName(key);
                    product.setAmount(map.get(key));
                    shoppingList.add(product);
                } else {
                    Double value = shoppingList.get(shoppingList.indexOf(key)).getAmount() + map.get(key);
                    product.setProductName(key);
                    product.setAmount(value);
                    shoppingList.add(product);
                }
            }


        }

        return shoppingList;


    }

    public Map<String,Double> hashMapDifference(Map<String, Double> a, Map<String, Double> b) {
        Iterator<String> bKeyIterator = b.keySet().iterator();
        String key;
        Double value;
        Map<String,Double> difference = new HashMap<>();

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
