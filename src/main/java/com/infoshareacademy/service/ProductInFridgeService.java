package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductInFridgeService {

    private final ProductInFridgeRepository productInFridgeRepository;
    private final FridgeService fridgeService;

    public ProductInFridgeService(ProductInFridgeRepository productInFridgeRepository, FridgeService fridgeService) {
        this.productInFridgeRepository = productInFridgeRepository;
        this.fridgeService = fridgeService;
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

        existingProduct.setFridge(fridgeService.addProductsToFridgeForm());
        existingProduct.setProductId(productInFridge.getProductId());
        existingProduct.setProductName(productInFridge.getProductName());
        existingProduct.setAmount(productInFridge.getAmount());
        existingProduct.setUnit(productInFridge.getUnit());
        existingProduct.setExpirationDate(productInFridge.getExpirationDate());
        productInFridgeRepository.save(existingProduct);
        return existingProduct;
    }



}
