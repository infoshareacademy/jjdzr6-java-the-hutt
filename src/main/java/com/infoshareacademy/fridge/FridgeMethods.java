package com.infoshareacademy.fridge;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infoshareacademy.recipe.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FridgeMethods {

    public void showAllProductsInFridge(Map map) {
        for (Object key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }
    }

/*    public void findInFridge(Fridge fridge, String search){


        List<Fridge> findInFridge = list.stream().filter(list -> list.getProductInFridge().keySet().equals(search)).collect(Collectors.toList());


    }*/


    public void writeJson(Fridge fridge) throws IOException {
        Path path = Path.of("src", "main", "java", "com",
                "infoshareacademy", "fridge", "products_in_fridge.json");
        File file = new File(path.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(file, fridge.getProductInFridge());
    }

    public List<Fridge> getJson() throws IOException {
        Path path = Path.of("src", "main", "java", "com",
                "infoshareacademy", "fridge", "products_in_fridge.json");
        ObjectMapper objectMapper = new ObjectMapper();
        String file = Files.readString(path);
        List<Fridge> fridge = objectMapper.readValue(file, new TypeReference<List<Fridge>>(){});
        return fridge;
    }
}
