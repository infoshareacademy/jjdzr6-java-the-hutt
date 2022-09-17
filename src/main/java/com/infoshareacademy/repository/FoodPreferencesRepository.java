package com.infoshareacademy.repository;

import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodPreferencesRepository extends JpaRepository<FoodPreferences, Long> {
}