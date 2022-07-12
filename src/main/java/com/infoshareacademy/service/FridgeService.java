package com.infoshareacademy.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.Json;
import com.infoshareacademy.fridge.Fridge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class FridgeService {

    public Fridge addProductToFridge() {
        Fridge fridge = new Fridge();
        Scanner scanner;
        boolean run = false;

        do {
            try {
                scanner = new Scanner(System.in);
                System.out.println("Nazwa produktu:");
                String fridgeProductName = scanner.nextLine();
                System.out.println("Ilość produktu (szt.):");
                scanner = new Scanner(System.in);
                double fridgeProductAmount = scanner.nextDouble();
                Map<String, Double> product = fridge.addFridgeProduct(fridgeProductName, fridgeProductAmount);
                fridge.setProductsInFridge(product);
                run = true;
            } catch (InputMismatchException exception) {
                System.out.println("Niepoprawny format odpowiedzi.");
                run = false;
            }
        } while (!run);
        System.out.println("Dodano produkt: " + fridge);
        return fridge;
    }

    public Fridge removeProductFromFridge() {
        Fridge fridge = new Fridge();
        FridgeService fridgeService = new FridgeService();
        Map<String, Double> result = null;
        try {
            result = fridgeService.getJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner;
        boolean run = false;

        do {
            try {
                scanner = new Scanner(System.in);
                System.out.println("Nazwa produktu:");
                String fridgeProductName = scanner.nextLine();
                result.remove(fridgeProductName);
                fridge.setProductsInFridge(result);
                run = true;
            } catch (InputMismatchException exception) {
                System.out.println("Niepoprawny format odpowiedzi.");
                run = false;
            }
        } while (!run);
        System.out.println("Usunięto produkt, w lodówce pozostało: )" + result);
        return fridge;
    }


    public void showAllProductsInFridge(Map map) {
        for (Object key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }
    }

    public void writeJson(Map<String, Double> fridge) throws IOException {

        Json.writeJson(fridge, "products_in_fridge.json");

    }

    public Map<String, Double> getJson() throws IOException {
        Path path = Path.of("src", "resources", "products_in_fridge.json");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path.toString());
        Map<String, Double> fridge = objectMapper.readValue(file, new TypeReference<Map<String, Double>>() {
        });
        return fridge;

    }
}