package com.infoshareacademy.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.Json;
import com.infoshareacademy.fridge.Fridge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class FridgeService {

    public void showAllProductsInFridge(Map map) {
        for (Object key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }
    }

    public void writeJson(Map<String,Double> fridge) throws IOException {
        Json.writeJson(fridge, "products_in_fridge.json");
    }

    public Map<String,Double> getJson() throws IOException {
        Path path = Path.of("src", "resources", "products_in_fridge.json");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path.toString());
        Map<String, Double> fridge = objectMapper.readValue(file, new TypeReference<Map<String, Double>>(){});
        return fridge;
    }
}
