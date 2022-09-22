package com.infoshareacademy.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.other.Json;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
public class FridgeService {

    private long userId = 1;
    private FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }


    public Fridge saveFridge(Fridge fridge) {
        fridge.setFridgeId(userId);
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

    public Optional<Fridge> findFridgeById(Long id) {
        return fridgeRepository.findById(id);
    }


    public long getUserId() {
        return userId;
    }
}