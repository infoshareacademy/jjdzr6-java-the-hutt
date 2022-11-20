package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.ProductInFridgeService;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/fridge")
public class ProductInFridgeController {

    Logger logger = LoggerFactory.getLogger(ProductInFridgeController.class);
    private final ProductInFridgeService productInFridgeService;
    private final FridgeService fridgeService;


    public ProductInFridgeController(ProductInFridgeService productInFridgeService, FridgeService fridgeService) {
        this.productInFridgeService = productInFridgeService;
        this.fridgeService = fridgeService;
    }

    @GetMapping
    public String productsInFridge(Model model, @SortDefault(value = "productName")  @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("fridgeProducts", fridgeService.getProductsInFridge(pageable));
        model.addAttribute("fridgeId", fridgeService.getDEFAULT_FRIDGE_ID());
        return "fridge";
    }

    @GetMapping("/product")
    public String addProductsToFridgeForm(Model model, FridgeDto fridgeDto) {
        fridgeService.addProductsToFridgeForm();
        model.addAttribute("fridgeDtoForm", fridgeDto);
        return "add-product-to-fridge";
    }

    @PostMapping(value = "/product", params = {"addProduct"})
    public String addProductsToFridge(@ModelAttribute("fridgeDtoForm") FridgeDto fridgeDto) {
        fridgeDto.addProductDto(new FridgeDto.ProductInFridgeDto());
        return "add-product-to-fridge";
    }

    @PostMapping(value = "/product", params = {"removeProduct"})
    public String removeProductFromFridgeForm(@ModelAttribute("fridge") Fridge fridge,
                                              HttpServletRequest request) {
        int index = Integer.parseInt(request.getParameter("removeProduct"));
        fridge.getProductsInFridge().remove(index);
        return "add-product-to-fridge";
    }

    @GetMapping("/{fridgeId}/{productId}")
    public String deleteProductFromFridge(@PathVariable Long productId, @PathVariable Long fridgeId) throws Exception {
        productInFridgeService.deleteProductInFridge(productId);
        return "redirect:/fridge";
    }

    @GetMapping("/product/{fridgeId}/{productId}")
    public String editProductInFridge(@PathVariable Long productId, Model model, @PathVariable Long fridgeId) throws NotFoundException {
        model.addAttribute("productInFridge", productInFridgeService.findProductInFridgeById(productId));
        model.addAttribute("fridgeId", fridgeService.getUserId());
        return "edit-products-in-fridge";
    }

    @PostMapping("/product/{fridgeId}/{productId}")
    public String editProductInFridge(Model model, @PathVariable Long productId,
                                      @ModelAttribute("productInFridge") FridgeDto.ProductInFridgeDto productInFridgeDto, @PathVariable Long fridgeId) throws NotFoundException {
        logger.info(productInFridgeDto.toString());
        model.addAttribute("fridgeId", fridgeService.getUserId());
        productInFridgeService.editProductInFridge(productId, productInFridgeDto);
        return "redirect:/fridge";
    }
    @PostMapping("/product")
    public String saveFridge(@Valid @ModelAttribute("fridgeDtoForm") FridgeDto fridgeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-product-to-fridge";
        }
        fridgeService.saveFridge(fridgeDto);
        return "redirect:/fridge";
    }
}
