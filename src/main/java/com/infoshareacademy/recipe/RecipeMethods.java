package com.infoshareacademy.recipe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infoshareacademy.fridge.Fridge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RecipeMethods {

/*    public void writeJson(Fridge fridge) throws IOException {
        Path path = Path.of("src", "main", "java", "com",
                "infoshareacademy", "recipe", "recipe.json");
        File file = new File(path.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(file, fridge.getProductInFridge());
    }*/

    public void getJson() throws IOException {
        Path path = Path.of("src", "main", "java", "com",
                "infoshareacademy", "recipe", "recipe.json");
        ObjectMapper objectMapper = new ObjectMapper();
        String file = Files.readString(path);
        System.out.println(file);

        /*        TypeReference<HashMap<String , Double>> typeReference = new TypeReference<HashMap<String, Double>>() {};
        Map<String, Double> map = objectMapper.readValue(file, typeReference);
        for(String key : map.keySet()){
            System.out.println(key + " = " + map.get(key));
        }*/
    }
}
