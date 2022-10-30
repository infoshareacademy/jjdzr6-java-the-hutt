package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FridgeService {

    private static final long DEFAULT_FRIDGE_ID = 1;
    private final FridgeRepository fridgeRepository;
    private final ProductInFridgeRepository productInFridgeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, ProductInFridgeRepository productInFridgeRepository,ModelMapper modelMapper) {
        this.fridgeRepository = fridgeRepository;
        this.productInFridgeRepository = productInFridgeRepository;
        this.modelMapper = modelMapper;
    }

    public FridgeDto saveFridge(FridgeDto fridgeDto) {
        fridgeDto.setFridgeId(getDEFAULT_FRIDGE_ID());
        fridgeDto.getProductsInFridgeDto().forEach(x -> x.setFridgeDto(fridgeDto));
        Fridge fridge = modelMapper.map(fridgeDto, Fridge.class);
        return modelMapper.map(fridgeRepository.save(fridge), FridgeDto.class);
    }

    public Optional<FridgeDto> getAllProductsFromFridge() {
        if (fridgeRepository.findById(DEFAULT_FRIDGE_ID).isPresent()) {
            return fridgeRepository.findById(DEFAULT_FRIDGE_ID).map((fridge -> modelMapper.map(fridge, FridgeDto.class)));
        } else {
            return Optional.of(new FridgeDto());
        }
    }

    public Optional<FridgeDto> addProductsToFridgeForm() {
        Optional<FridgeDto> fridgeDto;
        if (fridgeRepository.findById(getDEFAULT_FRIDGE_ID()).isPresent()) {
            return fridgeDto = fridgeRepository.findById(DEFAULT_FRIDGE_ID).map((fridge -> modelMapper.map(fridge, FridgeDto.class)));
        } else {
            fridgeDto = Optional.of(new FridgeDto());
            fridgeDto.get().setFridgeId(getDEFAULT_FRIDGE_ID());
            return fridgeDto;
        }
    }

    public Map<String, FridgeDto.ProductInFridgeDto> mapProductsInFridgeWithNameAsKey(){
        Map<String, FridgeDto.ProductInFridgeDto> productsInFridgeDto = getAllProductsFromFridge()
                .get().getProductsInFridgeDto()
                .stream()
                .peek(productInFridgeDto -> productInFridgeDto
                        .setProductName(productInFridgeDto.getProductName().toLowerCase()))
                .collect(Collectors.toMap(FridgeDto.ProductInFridgeDto::getProductName, Function.identity()));
        return productsInFridgeDto;
    }

    public Optional<FridgeDto> findFridgeById(Long id) {
        return fridgeRepository.findById(id).map(fridge -> modelMapper.map(fridge, FridgeDto.class));
    }

    public long getDEFAULT_FRIDGE_ID() {
        return DEFAULT_FRIDGE_ID;
    }
}