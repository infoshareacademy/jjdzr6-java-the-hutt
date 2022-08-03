package com.infoshareacademy.service;


import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class FridgeService {

    private FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository){
        this.fridgeRepository = fridgeRepository;
    }

    public List<Fridge> getAllProducts(){
        return fridgeRepository.findAll();
    }

    public Fridge addProductToFridge(Fridge fridge){
        return fridgeRepository.save(fridge);
    }

    public Fridge updateProductInFridge(Fridge fridge){
        return fridgeRepository.save(fridge);
    }

    public void deleteProductInFridge(Long id){
        fridgeRepository.deleteById(id);
    }



}