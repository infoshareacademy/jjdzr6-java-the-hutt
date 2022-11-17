package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FridgeService {

    private static final Long DEFAULT_FRIDGE_ID = 1L;
    private final FridgeRepository fridgeRepository;
    private final ProductInFridgeRepository productInFridgeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, ProductInFridgeRepository productInFridgeRepository, ModelMapper modelMapper) {
        this.fridgeRepository = fridgeRepository;
        this.productInFridgeRepository = productInFridgeRepository;
        this.modelMapper = modelMapper;
    }

    public Long getDEFAULT_FRIDGE_ID() {
        return DEFAULT_FRIDGE_ID;
    }

    public void saveFridge(FridgeDto fridgeDto) {
        Fridge fridge = modelMapper.map(fridgeDto, Fridge.class);
        fridge.getProductsInFridge().forEach(x -> x.setFridge(fridge));
        fridge.setFridgeId(getDEFAULT_FRIDGE_ID());
        fridgeRepository.save(fridge);
    }

    public FridgeDto getFridge() {
        FridgeDto fridgeDto;
        boolean flag = fridgeRepository.findById(DEFAULT_FRIDGE_ID).isPresent();
        if (flag) {
            Fridge fridge = fridgeRepository.findById(DEFAULT_FRIDGE_ID).get();
            fridgeDto = modelMapper.map(fridge, FridgeDto.class);
        } else {
            fridgeDto = new FridgeDto();
            fridgeDto.setFridgeId(DEFAULT_FRIDGE_ID);
        }
        return fridgeDto;
    }

    public Page<ProductInFridgeDto> getProductsInFridge(Pageable pageable) {
        Page<ProductInFridgeDto> collect = new PageImpl<>(new ArrayList<>());
        boolean flag = productInFridgeRepository.findProductInFridgeByFridge(DEFAULT_FRIDGE_ID, pageable).isEmpty();
        if (!flag) {
            Page<ProductInFridge> fridgeProducts = productInFridgeRepository.findProductInFridgeByFridge(DEFAULT_FRIDGE_ID, pageable);
            collect = fridgeProducts.map(productInFridge -> modelMapper.map(productInFridge, ProductInFridgeDto.class));
        }
        return collect;
    }

    public FridgeDto addProductsToFridgeForm() {
        FridgeDto fridgeDto;
        if (fridgeRepository.findById(getDEFAULT_FRIDGE_ID()).isPresent()) {
            fridgeDto = fridgeRepository
                    .findById(DEFAULT_FRIDGE_ID)
                    .map((fridge -> modelMapper.map(fridge, FridgeDto.class))).get();
        } else {
            fridgeDto = new FridgeDto();
            fridgeDto.setFridgeId(getDEFAULT_FRIDGE_ID());
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