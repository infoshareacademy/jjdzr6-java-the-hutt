package com.infoshareacademy.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infoshareacademy.entity.food_preferences.AllergenName;
import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.food_preferences.Meat;
import com.infoshareacademy.repository.AllergenNameRepository;
import com.infoshareacademy.repository.MeatRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class FoodPreferencesService {
    private AllergenNameRepository allergenNameRepository;
    private MeatRepository meatRepository;

    public AllergenName getAllergenName(long id) {

        return allergenNameRepository.findById(id).get();
    }

    public Meat getMeat(long id) {

        return meatRepository.findById(id).get();
    }


    public AllergenName setAllergenPreferences(AllergenName allergenName) {
        return allergenNameRepository.save(allergenName);
    }

    public Meat setMeatPreferences(Meat meat) {
        return meatRepository.save(meat);
    }


}
