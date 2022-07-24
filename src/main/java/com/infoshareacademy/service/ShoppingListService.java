package com.infoshareacademy.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.other.Json;
import com.infoshareacademy.entity.shopping_list.ShoppingList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ShoppingListService {

    public void writeJson(ShoppingList shoppingList) throws IOException {
        Json.writeJson(shoppingList,"products_in_fridge.json");
    }

    public void getJson() throws IOException  {
        Path path = Path.of("src", "main", "resources", "shopping_list.json");
        ObjectMapper objectMapper = new ObjectMapper();
        String file = Files.readString(path);
        TypeReference<HashMap<String , Double>> typeReference = new TypeReference<HashMap<String, Double>>() {};
        Map<String, Double> map = objectMapper.readValue(file, typeReference);
        for(String key : map.keySet()){
            System.out.println(key + " = " + map.get(key));
        }
    }
}
