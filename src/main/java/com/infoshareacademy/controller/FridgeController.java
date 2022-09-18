package com.infoshareacademy.controller;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("fridge")
public class FridgeController {

    private FridgeService fridgeService;
    private FridgeRepository fridgeRepository;

    @Autowired
    public FridgeController(FridgeService fridgeService, FridgeRepository fridgeRepository) {
        this.fridgeService = fridgeService;
        this.fridgeRepository = fridgeRepository;
    }

    @GetMapping
    public String productsInFridge(Model model) {
        model.addAttribute("productsinfridge", fridgeService.getAllProductsFromFridge());
        return "fridge";
    }

    @GetMapping("/new")
    public String addProductsToFridgeForm(Model model) {
        Fridge fridge;
        if (fridgeService.findFridgeById(1L).isPresent()) {
            fridge = fridgeService.getAllProductsFromFridge();
        } else {
            fridge = new Fridge();
            fridge.setFridgeId(fridgeService.getUserId());
        }
        model.addAttribute("fridge", fridge);
        return "addproductstofridge";
    }

    @PostMapping("/new")
    public String saveFridge(@Valid @ModelAttribute("fridge") Fridge fridge, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addproductstofridge";
        }
        fridge.setFridgeId(1L);
        fridgeService.saveFridge(fridge);
        return "redirect:/fridge";
    }

    @PostMapping(value = "/new", params = {"addProduct"})
    public String addProduct(@ModelAttribute("fridge") Fridge fridge) {
        fridge.addProduct(new ProductInFridge());
        return "addproductstofridge";
    }

    @PostMapping(value = "/new", params = {"removeProduct"})
    public String removeProduct(@ModelAttribute("fridge") Fridge fridge,
                                HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removeProduct"));
        fridge.getProductsInFridge().remove(index);
        return "addproductstofridge";
    }
}
