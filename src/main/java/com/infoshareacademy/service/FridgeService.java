package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FridgeService {

    private static final Long DEFAULT_FRIDGE_ID = 1L;
    private final FridgeRepository fridgeRepository;
    private final ProductInFridgeRepository productInFridgeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, ProductInFridgeRepository productInFridgeRepository,ModelMapper modelMapper) {
        this.fridgeRepository = fridgeRepository;
        this.productInFridgeRepository = productInFridgeRepository;
        this.modelMapper = modelMapper;
    }

    public void saveFridge(FridgeDto fridgeDto) {
        Fridge fridge = modelMapper.map(fridgeDto, Fridge.class);
        fridge.getProductsInFridge().forEach(x -> x.setFridge(fridge));
        fridge.setFridgeId(getDEFAULT_FRIDGE_ID());
        fridgeRepository.save(fridge);
    }

    public FridgeDto getFridge() {
        boolean flag = fridgeRepository.findById(DEFAULT_FRIDGE_ID).isPresent();
        FridgeDto fridgeDto;
        if (flag) {
            Fridge fridge = fridgeRepository.findById(DEFAULT_FRIDGE_ID).get();
            fridgeDto = modelMapper.map(fridge, FridgeDto.class);
        } else {
            fridgeDto = new FridgeDto();
            fridgeDto.setFridgeId(DEFAULT_FRIDGE_ID);
        }
        return fridgeDto;
    }

    public List<ProductInFridgeDto> getProductsInFridge(){
        List<ProductInFridgeDto> collect;
        boolean flag = fridgeRepository.findById(DEFAULT_FRIDGE_ID).isPresent();
        if(flag){
            List<ProductInFridge> fridgeProducts = fridgeRepository.findById(DEFAULT_FRIDGE_ID).get().getProductsInFridge();
            collect = fridgeProducts
                    .stream().map(productInFridge -> modelMapper.map(productInFridge, ProductInFridgeDto.class)).collect(Collectors.toList());
        } else{
            collect = new ArrayList<>();
        }
        return collect;
    }

    public FridgeDto addProductsToFridgeForm() {
        FridgeDto fridgeDto;
        if(fridgeRepository.findById(getDEFAULT_FRIDGE_ID()).isPresent()) {
            fridgeDto = fridgeRepository
                    .findById(DEFAULT_FRIDGE_ID)
                    .map((fridge -> modelMapper.map(fridge, FridgeDto.class))).get();
            return fridgeDto;
        } else {
            fridgeDto = new FridgeDto();
            fridgeDto.setFridgeId(getDEFAULT_FRIDGE_ID());
            return fridgeDto;
        }
    }

    public Map<String, FridgeDto.ProductInFridgeDto> mapProductsInFridgeWithNameAsKey(){
        List<FridgeDto.ProductInFridgeDto> productsInFridgeDto = getFridge().getProductsInFridge();
        if(productsInFridgeDto == null){
            productsInFridgeDto = new ArrayList<>();
        }
        Map<String, FridgeDto.ProductInFridgeDto> productsDto = productsInFridgeDto
                .stream()
                .peek(productInFridgeDto -> productInFridgeDto
                        .setProductName(productInFridgeDto.getProductName().toLowerCase()))
                .collect(Collectors.toMap(FridgeDto.ProductInFridgeDto::getProductName, Function.identity()));
        return productsDto;
    }

    public Optional<FridgeDto> findFridgeById(Long id) {
        return fridgeRepository.findById(id).map(fridge -> modelMapper.map(fridge, FridgeDto.class));
    }

    public long getDEFAULT_FRIDGE_ID() {
        return DEFAULT_FRIDGE_ID;
    }
}