package com.infoshareacademy.service;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class FridgeService {

    private final long userId = 1;
    private final FridgeRepository fridgeRepository;
    private final ProductInFridgeRepository productInFridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, ProductInFridgeRepository productInFridgeRepository) {
        this.fridgeRepository = fridgeRepository;
        this.productInFridgeRepository = productInFridgeRepository;
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

    public Optional<Fridge> findFridgeById(Long id) {
        return fridgeRepository.findById(id);
    }

    public long getUserId() {
        return userId;
    }

    public ProductInFridge findProductInFridgeById(Long productId) throws Exception {
        return productInFridgeRepository.findById(productId)
                .orElseThrow(() -> new Exception("Not found Product in Fridge for"
                        + "ID: " + productId));
    }

    public void deleteProductFromFridge(Long productId) throws Exception{
        System.out.println(fridgeRepository.findById(productId));
        productInFridgeRepository.deleteById(productId);
    }
}