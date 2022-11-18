package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import com.infoshareacademy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FridgeService {

    private static Long USER_ID;
    private final FridgeRepository fridgeRepository;

    private final UserRepository userRepository;
    private final ProductInFridgeRepository productInFridgeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, UserRepository userRepository, ProductInFridgeRepository productInFridgeRepository, ModelMapper modelMapper) {
        this.fridgeRepository = fridgeRepository;
        this.userRepository = userRepository;
        this.productInFridgeRepository = productInFridgeRepository;
        this.modelMapper = modelMapper;
    }

    public Long getUSER_ID() {
        Long id = 1L;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        if (userRepository.findByUsername(userDetail.getUsername()).isPresent()) {
            id = userRepository.findByUsername(userDetail.getUsername()).get().getId();
        }
        return id;
    }

    public void saveFridge(FridgeDto fridgeDto) {
        Fridge fridge = modelMapper.map(fridgeDto, Fridge.class);
        fridge.getProductsInFridge().forEach(x -> x.setFridge(fridge));
        fridge.setFridgeId(getUSER_ID());
        fridgeRepository.save(fridge);
    }

    public FridgeDto getFridge() {
        FridgeDto fridgeDto;
        boolean flag = fridgeRepository.findById(getUSER_ID()).isPresent();
        if (flag) {
            Fridge fridge = fridgeRepository.findById(getUSER_ID()).get();
            fridgeDto = modelMapper.map(fridge, FridgeDto.class);
        } else {
            fridgeDto = new FridgeDto();
            fridgeDto.setFridgeId(getUSER_ID());
        }
        return fridgeDto;
    }

    public List<ProductInFridgeDto> getProductsInFridge() {
        List<ProductInFridgeDto> collect;
        boolean flag = fridgeRepository.findById(getUSER_ID()).isPresent();
        if (flag) {
            List<ProductInFridge> fridgeProducts = fridgeRepository.findById(getUSER_ID()).get().getProductsInFridge();
            collect = fridgeProducts
                    .stream().map(productInFridge -> modelMapper.map(productInFridge, ProductInFridgeDto.class)).collect(Collectors.toList());
        } else {
            collect = new ArrayList<>();
        }
        return collect;
    }

    public FridgeDto addProductsToFridgeForm() {
        FridgeDto fridgeDto;
        if (fridgeRepository.findById(getUSER_ID()).isPresent()) {
            fridgeDto = fridgeRepository
                    .findById(getUSER_ID())
                    .map((fridge -> modelMapper.map(fridge, FridgeDto.class))).get();
        } else {
            fridgeDto = new FridgeDto();
            fridgeDto.setFridgeId(getUSER_ID());
        }
        return fridgeDto;
    }

    public Map<String, FridgeDto.ProductInFridgeDto> mapProductsInFridgeWithNameAsKey() {
        List<FridgeDto.ProductInFridgeDto> productsInFridgeDto = getFridge().getProductsInFridge();
        if (productsInFridgeDto == null) {
            productsInFridgeDto = new ArrayList<>();
        }
        Map<String, FridgeDto.ProductInFridgeDto> productsDto = productsInFridgeDto
                .stream()
                .peek(productInFridgeDto -> productInFridgeDto
                        .setProductName(productInFridgeDto.getProductName().toLowerCase()))
                .collect(Collectors.toMap(FridgeDto.ProductInFridgeDto::getProductName, Function.identity()));
        return productsDto;
    }

}