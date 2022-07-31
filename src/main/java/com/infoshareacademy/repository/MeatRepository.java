package com.infoshareacademy.repository;

import com.infoshareacademy.entity.food_preferences.Meat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeatRepository extends JpaRepository<Meat,Long> {
}
