package com.infoshareacademy.service;


import com.infoshareacademy.entity.fridge.Fridge;
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

    Fridge fridge = new Fridge();

    public List<ProductInFridge> getProductsFromFridge(){
        return fridgeRepository.findById(0L).get().getProductList();
    }

    public Fridge saveFridge(Fridge fridge){
        fridge.getProductList().forEach(element -> element.setFridge(this.fridge));
        return fridgeRepository.save(this.fridge);
    }

    public Fridge updateFridge(Fridge fridge){
        fridge.getProductList().forEach(element -> element.setFridge(this.fridge));
        return fridgeRepository.save(this.fridge);
    }

//    public void deleteProductInFridge(Long id){
//        fridgeRepository.deleteById(id);
//    }

    public Fridge getFridgeById(Long id){
        return fridgeRepository.findById(0L).get();
    }
}