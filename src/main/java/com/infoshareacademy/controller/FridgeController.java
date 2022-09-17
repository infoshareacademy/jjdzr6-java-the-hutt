package com.infoshareacademy.controller;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.Product;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class FridgeController {

    private FridgeService fridgeService;

    @Autowired
    public FridgeController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping("/fridge_products")
    public String allProductsInFridge(Model model){
        model.addAttribute("fridge", fridgeService.getFridge());
        return "productsinfridge";
    }

    @GetMapping("/fridge_products/new")
    public String newProductInFridgeForm(Model model){
        ProductInFridge productInFridge = new ProductInFridge();
        model.addAttribute("product_fridge", productInFridge);
        return "new_product_in_fridge";
    }

    @PostMapping("/fridge_products")
    public String saveProductInFridge(@ModelAttribute("product_fridge") ProductInFridge productInFridge, Fridge fridge){
        fridge.setFridgeId(fridge.getFridgeId());
        fridge.addProductToFridge(productInFridge);
        fridgeService.saveFridge(fridge);
        return "redirect:/fridge_products";
    }

//    @GetMapping("/fridge_products/{id}")
//    public String deleteProductInFridge(@PathVariable Long id){
//        fridgeService.deleteProductInFridge(id);
//        return "redirect:/fridge_products";
//    }

//    @GetMapping("/fridge_products/edit/{id}")
//    public String updateProductInFridgeForm(@PathVariable Long id, Model model){
//        model.addAttribute("product_fridge", fridgeService.getProductInFridgeById(id));
//        return "update_product_in_fridge";
//    }

//    @PostMapping("/fridge_products/{id}")
//    public String updateProductInFridge(@PathVariable Long id, @ModelAttribute("product_fridge") ProductInFridge productInFridge, Model model){
//        ProductInFridge existingProduct = fridgeService.getProductInFridgeById(id);
//
//        existingProduct.setProductInFridgeId(id);
//        existingProduct.setProductName(productInFridge.getProductName());
//        existingProduct.setAmount(productInFridge.getAmount());
//
//        fridgeService.updateProductInFridge(existingProduct);
//
//        return "redirect:/fridge_products";
//    }

}
