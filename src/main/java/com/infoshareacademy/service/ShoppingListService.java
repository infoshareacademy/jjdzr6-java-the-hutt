package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.shopping_list.ShoppingList;
import com.infoshareacademy.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final FridgeService fridgeService;


    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository, FridgeService fridgeService) {
        this.shoppingListRepository = shoppingListRepository;
        this.fridgeService = fridgeService;

    }

    public List<ShoppingList> findAllShoppingLists() {
        return shoppingListRepository.findByUserId(fridgeService.getUserId());
    }

    public void saveShoppingList(ShoppingList shoppingList) {
        shoppingList.getShoppingProductList().forEach(x -> x.setShoppingList(shoppingList));
        shoppingList.setUserId(fridgeService.getUserId());
        shoppingListRepository.save(shoppingList);
    }

    public void deleteShoppingListById(Long shoppingListId) {
        shoppingListRepository.deleteShoppingListById(shoppingListId);
    }

    public ShoppingList getShoppingList(Long id) {
        ShoppingList shoppingList = new ShoppingList();
        if (shoppingListRepository.findById(id).isPresent()) shoppingList = shoppingListRepository.findById(id).get();
        return shoppingList;
    }

    public ShoppingList viewShoppingList(Long id) {
        ShoppingList shoppingList = new ShoppingList();
        addProductsFromRecipesToShoppingList(id);
        if (shoppingListRepository.findById(id).isPresent()) shoppingList = shoppingListRepository.findById(id).get();
        return shoppingList;
    }

    public void addRecipeToShoppingList(Recipe recipe, Long id) {

        ShoppingList shoppingList = getShoppingList(id);
        List<Recipe> shoppingListRecipe = shoppingList.getShoppingListRecipe();

        if (!shoppingListRecipe.contains(recipe)) {
            shoppingListRecipe.add(recipe);
        }
        saveShoppingList(shoppingList);
    }

    public void addProductsFromRecipesToShoppingList(Long id) {
        List<Recipe> shoppingListRecipe = getShoppingList(id).getShoppingListRecipe();
        List<ProductShoppingList> productsFromRecipe = compareListOfRecipeAndProductsFromFridge(shoppingListRecipe);
        ShoppingList shoppingList = getShoppingList(id);
        deleteProductsFromShoppingList(id);
        shoppingList.setShoppingProductList(productsFromRecipe);
        for (ProductShoppingList list : shoppingList.getShoppingProductList()) {
            list.setShoppingList(shoppingList);
        }
        shoppingListRepository.save(shoppingList);

    }

    public void deleteProductsFromShoppingList(Long id) {
        shoppingListRepository.deleteProductsFromShoppingList(id);
    }

    public List<ProductShoppingList> compareListOfRecipeAndProductsFromFridge(List<Recipe> recipeList) {

        List<ProductInFridge> productsInFridge = fridgeService.getAllProductsFromFridge().getProductsInFridge();
        Map<String, Double> fridgeOne = productsInFridge.stream().collect(Collectors.toMap(ProductInFridge::getProductName, ProductInFridge::getAmount));

        Map<String, Double> fridgeMap = new HashMap<>();
        for (String key : fridgeOne.keySet()) {
            if (fridgeMap.get(key) == null) {
                fridgeMap.putIfAbsent(key, fridgeOne.get(key));
            } else {
                Double value = fridgeMap.get(key) + fridgeOne.get(key);
                fridgeMap.put(key, value);
            }
        }

        Map<String, Double> recipeMap = new HashMap<>();
        Map<String, ProductUnit> unitsMap = new HashMap<>();
        List<ProductShoppingList> shoppingList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            Map<String, Double> recipeOne = recipe.getProductList().stream().collect(Collectors.toMap(ProductRecipe::getProductName, ProductRecipe::getAmount));
            Map<String, ProductUnit> recipeUnits = recipe.getProductList().stream().collect(Collectors.toMap(ProductRecipe::getProductName, ProductRecipe::getUnit));
            for (String key : recipeOne.keySet()) {
                if (recipeMap.get(key) == null) {
                    recipeMap.putIfAbsent(key, recipeOne.get(key));
                    unitsMap.putIfAbsent(key, recipeUnits.get(key));
                } else {
                    Double value = recipeMap.get(key) + recipeOne.get(key);
                    recipeMap.put(key, value);
                }
            }
        }
        Map<String, Double> map = hashMapDifference(fridgeMap, recipeMap);
        for (String key : map.keySet()) {
            ProductShoppingList product = new ProductShoppingList();
            product.setProductName(key);
            product.setAmount(map.get(key));
            product.setUnit(unitsMap.get(key));
            shoppingList.add(product);
        }
        return shoppingList;
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