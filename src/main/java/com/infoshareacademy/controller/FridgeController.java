package com.infoshareacademy.controller;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/fridge")
public class FridgeController {

    private final FridgeService fridgeService;
    private final FridgeRepository fridgeRepository;

    @Autowired
    public FridgeController(FridgeService fridgeService, FridgeRepository fridgeRepository) {
        this.fridgeService = fridgeService;
        this.fridgeRepository = fridgeRepository;
    }

    @PostMapping("/product")
    public String saveFridge(@Valid @ModelAttribute("fridgeDtoForm") FridgeDto fridgeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addproductstofridge";
        }
        fridgeService.saveFridge(fridgeDto);
        return "redirect:/fridge";
    }
}
