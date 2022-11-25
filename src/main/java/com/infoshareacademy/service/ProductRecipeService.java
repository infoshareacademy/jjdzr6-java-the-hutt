package com.infoshareacademy.service;

import com.infoshareacademy.DTO.ProductRecipeDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.repository.ProductRecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductRecipeService {

    private final ProductRecipeRepository productRepository;

    private final RecipeService recipeService;

    private final ModelMapper modelMapper;

    @Autowired
    public ProductRecipeService(ProductRecipeRepository productRepository, RecipeService recipeService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
    }

    public List<ProductRecipeDto> getAllProductRecipeByRecipeId(final Long recipeId) {

        if (productRepository.findAllProductsByRecipeRecipeId(recipeId).stream().findFirst().isPresent()) {
            return productRepository.findAllProductsByRecipeRecipeId(recipeId)
                    .stream().map(productRecipe -> modelMapper.map(productRecipe, ProductRecipeDto.class)).toList();
        } else {
            List<ProductRecipeDto> products = new ArrayList<>();
            ProductRecipeDto product = new ProductRecipeDto(" ", 0.0);
            product.setRecipe(modelMapper.map(recipeService.getRecipeById(recipeId), ProductRecipeDto.RecipeDto.class));
            products.add(product);
            return products;
        }
    }

    public ProductRecipeDto findProductRecipeById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .map(productRecipe -> modelMapper.map(productRecipe, ProductRecipeDto.class)).orElseThrow(() -> new Exception("Not found Product Recipe for "
                        + "ID: " + productId));
    }

    public void deleteProductRecipe(Long productId) throws Exception {
        ProductRecipeDto product = findProductRecipeById(productId);
        productRepository.delete(modelMapper.map(product, ProductRecipe.class));
        log.info("Usunięto produkt z przpisu o id: " + productId);
    }

    public void saveProductRecipe(RecipeDto.ProductRecipeDto productRecipeDto, RecipeDto recipe) {
        if (productRecipeDto != null) {
            productRecipeDto.setRecipeDto(recipe);
            ProductRecipe productRecipe = modelMapper.map(productRecipeDto, ProductRecipe.class);
            productRepository.save(productRecipe);
        }
        log.info("Zapisano produkt o id: " + productRecipeDto.getProductId() + " dla przepisu o id: " + recipe.getRecipeId());
    }
}