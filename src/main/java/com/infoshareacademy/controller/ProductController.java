package com.infoshareacademy.controller;

import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/my_products")
    public String productsAll(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "all_products";
    }
}
