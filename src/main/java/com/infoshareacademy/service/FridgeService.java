package com.infoshareacademy.service;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FridgeService {

    private final long userId = 1;
    private final FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }


    public Fridge saveFridge(Fridge fridge) {
        fridge.setFridgeId(getUserId());
        fridge.getProductsInFridge().forEach(x -> x.setFridge(fridge));
        return fridgeRepository.save(fridge);
    }

    public Fridge getAllProductsFromFridge() {
        if (fridgeRepository.findById(userId).isPresent()) {
            return fridgeRepository.findById(userId).get();
        } else {
            return new Fridge();
        }
    }

    public Fridge addProductsToFridgeForm() {
        Fridge fridge;
        if (fridgeRepository.findById(getUserId()).isPresent()) {
            return fridge = fridgeRepository.findById(getUserId()).get();
        } else {
            fridge = new Fridge();
            fridge.setFridgeId(getUserId());
            return fridge;
        }
    }

    public Map<String, ProductInFridge> mapProductsInFridgeWithNameAsKey(){
        Map<String, ProductInFridge> productsInFridge = getAllProductsFromFridge()
                .getProductsInFridge()
                .stream()
                .peek(productInFridge -> productInFridge
                        .setProductName(productInFridge.getProductName().toLowerCase()))
                .collect(Collectors.toMap(ProductInFridge::getProductName, Function.identity()));
        return productsInFridge;
    }

    public Optional<Fridge> findFridgeById(Long id) {
        return fridgeRepository.findById(id);
    }


    public long getUserId() {
        return userId;
    }
}