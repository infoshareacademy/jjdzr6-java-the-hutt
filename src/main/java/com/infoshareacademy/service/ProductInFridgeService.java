package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
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

    public ProductInFridgeDto findProductInFridgeById(Long productId) throws NotFoundException {
        Optional<ProductInFridgeDto> productInFridgeDto = Optional.ofNullable(productInFridgeRepository.findById(productId)
                .map(productInFridge -> modelMapper.map(productInFridge, ProductInFridgeDto.class))
                .orElseThrow(() -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId))));

        return productInFridgeDto.get();
    }

    public void deleteProductInFridge(Long productId) throws NotFoundException {
        if (productInFridgeRepository.findById(productId).isPresent()) {
            productInFridgeRepository.deleteById(productId);
        } else throw new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId));
    }

    public void editProductInFridge(Long productId, FridgeDto.ProductInFridgeDto productInFridgeDto) throws NotFoundException {
        Optional<ProductInFridge> productInFridge = Optional.ofNullable(productInFridgeRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId))));

        Optional<ProductInFridgeDto> productDto = productInFridge
                .map(product -> modelMapper.map(product, ProductInFridgeDto.class));

        ProductInFridgeDto.FridgeDto fridgeDto = modelMapper.map(fridgeRepository
                .findById(fridgeService.getUSER_ID()).get(), ProductInFridgeDto.FridgeDto.class);
        productDto.get().setFridgeDto(fridgeDto);
        productDto.get().setProductId(productInFridgeDto.getProductId());
        productDto.get().setProductName(productInFridgeDto.getProductName());
        productDto.get().setAmount(productInFridgeDto.getAmount());
        productDto.get().setUnit(productInFridgeDto.getUnit());
        productDto.get().setExpirationDate(LocalDate.parse(productInFridgeDto.getExpirationDate().toString()));

        ProductInFridge product = modelMapper.map(productDto, ProductInFridge.class);
        productInFridgeRepository.save(product);
    }
}
