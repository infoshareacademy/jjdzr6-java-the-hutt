package com.infoshareacademy.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.Json;
import com.infoshareacademy.recipe.Recipe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShoppingListService {

    public  void writeJson(Map<String,Double> shoppingList) throws IOException {
        Json.writeJson(shoppingList,"shopping_list.json");
    }

    public void getJson() throws IOException  {
        Path path = Path.of("src", "main", "resources", "shopping_list.json");
        ObjectMapper objectMapper = new ObjectMapper();
        String file = Files.readString(path);
        TypeReference<HashMap<String , Double>> typeReference = new TypeReference<>() {
        };
        Map<String, Double> map = objectMapper.readValue(file, typeReference);
        for(String key : map.keySet()){
            System.out.println(key + " = " + map.get(key));
        }
    }

    public void createShoppingList() throws IOException {

        RecipeService recipeService = new RecipeService();
        List<Recipe> recipeList = recipeService.getJson();

        FridgeService fridgeService = new FridgeService();
        Map<String, Double> fridgeMap = fridgeService.getJson();

        Map<String, Double> recipeMap = new HashMap<>();
        Map<String, Double> shoppingList = null;
        for (Recipe recipe : recipeList) {
            Map<String, Double> recipeOne = recipe.getNeccesaryProducts();

            for (String key : recipeOne.keySet()) {
                if(recipeMap.get(key) == null){
                    recipeMap.putIfAbsent(key, recipeOne.get(key));
                } else {
                Double value = recipeMap.get(key)+recipeOne.get(key);
                recipeMap.put(key, value);
            }}
            shoppingList = hashMapDifference(fridgeMap, recipeMap);
        }
        System.out.println(shoppingList);
        writeJson(shoppingList);

    }

    public Map hashMapDifference(Map<String, Double> a, Map<String, Double> b) {
        Iterator<String> bKeyIterator = b.keySet().iterator();
        String key;
        Double value;
        HashMap difference = new HashMap();

        while (bKeyIterator.hasNext()) {
            key = bKeyIterator.next();
                if(a.get(key) == null){
                    value = b.get(key);
                } else {
                    value = b.get(key) - a.get(key);
                }
                if(value > 0){
                    difference.put(key, value);
                } 
        }
        return difference;
    }



}
