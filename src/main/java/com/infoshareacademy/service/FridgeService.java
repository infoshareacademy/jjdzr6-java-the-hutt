package com.infoshareacademy.service;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FridgeService {

    private static final long DEFAULT_FRIDGE_ID = 1;
    private final FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
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
}