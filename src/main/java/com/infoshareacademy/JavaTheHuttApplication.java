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
public class JavaTheHuttApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(JavaTheHuttApplication.class, args);


    }

    @Autowired
    FridgeService fridgeService;
    @Autowired
    FridgeRepository fridgeRepository;
    @Autowired
    ProductInFridgeService productInFridgeService;
    @Autowired
    ProductInFridgeRepository productInFridgeRepository;

    @Override
    public void run(String... args) throws Exception {

        Fridge fridge = new Fridge();
        fridge.setFridgeId(1L);
        ProductInFridge productInFridge = new ProductInFridge();
        productInFridge.setProductName("ff");
        productInFridge.setAmount(1.0);
        productInFridge.setProductId(1L);
        productInFridge.setUnit(ProductUnit.GRAM);
        productInFridge.setExpirationDate(LocalDate.now().plusDays(1));
        List<ProductInFridge> productInFridgeList = List.of(productInFridge);

        fridge.setProductsInFridge(productInFridgeList);
        productInFridge.setFridge(fridge);

        fridgeRepository.save(fridge);
        productInFridgeRepository.save(productInFridge);




    }
}
