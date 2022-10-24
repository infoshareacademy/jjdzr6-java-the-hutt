package com.infoshareacademy.controller;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.ProductInFridgeService;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/fridge")
public class ProductInFridgeController {

    private final ProductInFridgeService productInFridgeService;
    private final FridgeService fridgeService;


    public ProductInFridgeController(ProductInFridgeService productInFridgeService, FridgeService fridgeService) {
        this.productInFridgeService = productInFridgeService;
        this.fridgeService = fridgeService;
    }

    @GetMapping
    public String productsInFridge(Model model) {
        model.addAttribute("productsinfridge", fridgeService.getAllProductsFromFridge());
        return "fridge";
    }

    @GetMapping("/product")
    public String addProductsToFridgeForm(Model model, Fridge fridge) {
        fridgeService.addProductsToFridgeForm();
        model.addAttribute("fridge", fridge);
        return "addproductstofridge";
    }

    @PostMapping(value = "/product", params = {"addProduct"})
    public String addProduct(@ModelAttribute("fridge") Fridge fridge) {
        fridge.addProduct(new ProductInFridge());
        return "addproductstofridge";
    }

    @PostMapping(value = "/product", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute("fridge") Fridge fridge,
                                HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removeProduct"));
        fridge.getProductsInFridge().remove(index);
        return "addproductstofridge";
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
