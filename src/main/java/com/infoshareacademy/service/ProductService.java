package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.repository.ProductRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRecipeRepository productRepository;

    private final RecipeService recipeService;

    @Autowired
    public ProductService(ProductRecipeRepository productRepository, RecipeService recipeService) {
        this.productRepository = productRepository;
        this.recipeService = recipeService;
    }

    public List<ProductRecipe> getAllProductByRecipeId(final Long recipeId) {

        if (productRepository.findAllProductsByRecipeRecipeId(recipeId).stream().findFirst().isPresent()) {
            return productRepository.findAllProductsByRecipeRecipeId(recipeId);
        } else {
            List<ProductRecipe> products = new ArrayList<>();
            ProductRecipe product = new ProductRecipe(" ", 0.0);
            product.setRecipe(recipeService.getRecipeById(recipeId));
            products.add(product);
            return products;
        }
    }

    public ProductRecipe findById(Long productId) throws Exception {
        return productRepository.findById(productId).orElseThrow(() -> new Exception("Not found Product Recipe for "
                + "ID: " + productId));
    }

    public void deleteProductRecipe(Long productId) throws Exception {
        ProductRecipe product = findById(productId);
        productRepository.delete(product);
    }

    public void saveProductRecipe(ProductRecipe productRecipe) {
        if (productRecipe != null) productRepository.save(productRecipe);
    }
}