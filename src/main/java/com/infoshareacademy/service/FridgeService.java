package com.infoshareacademy.service;


import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FridgeService {

    private FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }

    public List<ProductInFridge> getProductsFromFridge(){
        return fridgeRepository.findAll();
    }

    public ProductInFridge addProductToFridge(ProductInFridge productInFridge){
        return fridgeRepository.save(productInFridge);
    }

    public ProductInFridge updateProductInFridge(ProductInFridge productInFridge){
        return fridgeRepository.save(productInFridge);
    }

    public void deleteProductInFridge(Long id){
        fridgeRepository.deleteById(id);
    }

    public ProductInFridge getProductInFridgeById(Long id){
        return fridgeRepository.findById(id).get();
    }
}