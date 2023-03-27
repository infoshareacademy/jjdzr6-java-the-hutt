package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class ProductInFridgeService {

    private final ProductInFridgeRepository productInFridgeRepository;
    private final FridgeRepository fridgeRepository;
    private final FridgeService fridgeService;

    private final ModelMapper modelMapper;

    public ProductInFridgeService(ProductInFridgeRepository productInFridgeRepository, FridgeRepository fridgeRepository,
                                  FridgeService fridgeService, ModelMapper modelMapper) {
        this.productInFridgeRepository = productInFridgeRepository;
        this.fridgeRepository = fridgeRepository;
        this.fridgeService = fridgeService;
        this.modelMapper = modelMapper;
    }


    public Optional<ProductInFridgeDto> findProductInFridgeById(Long productId) throws NotFoundException {

        return Optional.ofNullable(productInFridgeRepository.findById(productId)
                .map(productInFridge -> modelMapper.map(productInFridge, ProductInFridgeDto.class))
                .orElseThrow(() -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId))));
    }

    public void deleteProductInFridge(Long productId) throws NotFoundException {

        if (productInFridgeRepository.findById(productId).isPresent()) {
            productInFridgeRepository.deleteById(productId);
        } else throw new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId));
        log.info("Usunięto produkt o id: " + productId + " z lodówki dla użytkownika o id: " + fridgeService.getUserId());
    }

    public void editProductInFridge(Long productId, FridgeDto.ProductInFridgeDto productInFridgeDto) throws NotFoundException {
        Optional<ProductInFridge> productInFridge = Optional.ofNullable(productInFridgeRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId))));

        ProductInFridgeDto productDto = productInFridge
                .map(product -> modelMapper.map(product, ProductInFridgeDto.class)).orElse(new ProductInFridgeDto());
        Fridge fridge = fridgeRepository.findById(fridgeService.getUserId()).orElse(new Fridge());
        ProductInFridgeDto.FridgeDto fridgeDto = modelMapper.map(fridge, ProductInFridgeDto.FridgeDto.class);
        productDto.setFridgeDto(fridgeDto);
        productDto.setProductId(productInFridgeDto.getProductId());
        productDto.setProductName(productInFridgeDto.getProductName());
        productDto.setAmount(productInFridgeDto.getAmount());
        productDto.setUnit(productInFridgeDto.getUnit());
        productDto.setExpirationDate(LocalDate.parse(productInFridgeDto.getExpirationDate().toString()));

        ProductInFridge product = modelMapper.map(productDto, ProductInFridge.class);
        fridgeService.convertUnitsInProducts(product);
        productInFridgeRepository.save(product);
        log.info("Zaktualizowano produkty w lodówce dla użytkownika o id: " + fridgeService.getUserId());
    }


}
