package com.infoshareacademy.service;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FridgeService {

    private static final long DEFAULT_FRIDGE_ID = 1;
    private final FridgeRepository fridgeRepository;
    private final ProductInFridgeRepository productInFridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, ProductInFridgeRepository productInFridgeRepository) {
        this.fridgeRepository = fridgeRepository;
        this.productInFridgeRepository = productInFridgeRepository;
    }

    public Fridge saveFridge(Fridge fridge) {
        fridge.setFridgeId(getDEFAULT_FRIDGE_ID());
        fridge.getProductsInFridge().forEach(x -> x.setFridge(fridge));
        return fridgeRepository.save(fridge);
    }

    public Fridge getAllProductsFromFridge() {
        if (fridgeRepository.findById(DEFAULT_FRIDGE_ID).isPresent()) {
            return fridgeRepository.findById(DEFAULT_FRIDGE_ID).get();
        } else {
            return new Fridge();
        }
    }

    public Fridge addProductsToFridgeForm() {
        Fridge fridge;
        if (fridgeRepository.findById(getDEFAULT_FRIDGE_ID()).isPresent()) {
            return fridge = fridgeRepository.findById(getDEFAULT_FRIDGE_ID()).get();
        } else {
            fridge = new Fridge();
            fridge.setFridgeId(getDEFAULT_FRIDGE_ID());
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

    public long getDEFAULT_FRIDGE_ID() {
        return DEFAULT_FRIDGE_ID;
    }
}