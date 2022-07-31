package com.infoshareacademy.repository;

import com.infoshareacademy.entity.food_preferences.AllergenName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergenNameRepository extends JpaRepository<AllergenName, Long> {
}
