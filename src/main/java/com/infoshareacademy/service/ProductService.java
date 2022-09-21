package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.repository.ProductRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRecipeRepository productRepository;

    @Autowired
    public ProductService(ProductRecipeRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductRecipe> getAllProductByRecipeId(final Long recipeId) {
        return productRepository.findAllProductsByRecipeRecipeId(recipeId);
    }

    public ProductRecipe findById(Long productId) throws Exception {
        return productRepository.findById(productId).orElseThrow(() -> new Exception("Not found Product Recipe for "
                                                                                         + "ID: " + productId));
    }

    public void deleteProductRecipe(Long productId) throws Exception {
        ProductRecipe product = findById(productId);
        productRepository.delete(product);
    }

    public void saveProductRecipe(ProductRecipe productRecipe){
        if (productRecipe != null) productRepository.save(productRecipe);
    }
}