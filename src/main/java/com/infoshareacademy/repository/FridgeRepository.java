package com.infoshareacademy.repository;

import com.infoshareacademy.entity.fridge.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {
}
