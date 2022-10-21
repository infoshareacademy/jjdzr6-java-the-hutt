package com.infoshareacademy.controller;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.service.ProductInFridgeService;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fridge")
public class ProductInFridgeController {

    private final ProductInFridgeService productInFridgeService;

    public ProductInFridgeController(ProductInFridgeService productInFridgeService) {
        this.productInFridgeService = productInFridgeService;
    }

    @GetMapping("/{fridgeId}/{productId}")
    public String deleteProductFromFridge(@PathVariable Long productId,
                                          Long fridgeId) throws Exception {
        productInFridgeService.deleteProductFromFridge(productId);
        return "redirect:/fridge";
    }

    @GetMapping("/product/{fridgeId}/{productId}")
    public String editProductFromFridge(@PathVariable Long productId, Model model) throws NotFoundException {
        model.addAttribute("productInFridge", productInFridgeService.findProductInFridgeById(productId));
        return "edit-products-in-fridge";
    }

    @PostMapping("/product/{fridgeId}/{productId}")
    public String editProductFromFridge(@PathVariable Long productId, @ModelAttribute("productInFridge") ProductInFridge productInFridge){
        System.out.println(productInFridge);
        productInFridgeService.editProductFromFridge(productId, productInFridge);
        return "redirect:/fridge";
    }
}
