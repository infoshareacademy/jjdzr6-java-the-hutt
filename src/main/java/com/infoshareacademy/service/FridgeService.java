package com.infoshareacademy.service;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Optional<Fridge> findFridgeById(Long id) {
        return fridgeRepository.findById(id);
    }

    public long getDEFAULT_FRIDGE_ID() {
        return DEFAULT_FRIDGE_ID;
    }

    public ProductInFridge findProductInFridgeById(Long productId) throws NotFoundException {
        return productInFridgeRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId)));
    }

    public void deleteProductFromFridge(Long productId) throws NotFoundException {
        if (productInFridgeRepository.findById(productId).isPresent()) {
            productInFridgeRepository.deleteById(productId);
        } else throw new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId));
    }

    public ProductInFridge editProductFromFridge(Long productId, ProductInFridge productInFridge) {
        ProductInFridge existingProduct = new ProductInFridge();
        if (productInFridgeRepository.findById(productId).isPresent())
            existingProduct = productInFridgeRepository.findById(productId).get();

        existingProduct.setProductId(productInFridge.getProductId());
        existingProduct.setProductName(productInFridge.getProductName());
        existingProduct.setAmount(productInFridge.getAmount());
        existingProduct.setUnit(productInFridge.getUnit());
        existingProduct.setExpirationDate(productInFridge.getExpirationDate());
        productInFridgeRepository.save(existingProduct);
        return existingProduct;
    }

    public void saveProductFromFridge(ProductInFridge productInFridge) {
        if (productInFridge != null) productInFridgeRepository.save(productInFridge);
    }
}