package com.infoshareacademy;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.ProductInFridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class JavaTheHuttApplication{



    public static void main(String[] args) {
        SpringApplication.run(JavaTheHuttApplication.class, args);


    }

}
