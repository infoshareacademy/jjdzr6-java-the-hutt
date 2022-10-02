package com.infoshareacademy.service;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Optional<Fridge> findFridgeById(Long id) {
        return fridgeRepository.findById(id);
    }


    public long getUserId() {
        return userId;
    }
}