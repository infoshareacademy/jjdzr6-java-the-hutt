package com.infoshareacademy.service;


import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductShoppingListDto;
import com.infoshareacademy.DTO.ShoppingListDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.shopping_list.ShoppingList;
import com.infoshareacademy.repository.ProductShoppingListRepository;
import com.infoshareacademy.repository.ShoppingListRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final FridgeService fridgeService;
    private final ProductShoppingListRepository productShoppingListRepository;

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;


    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository, FridgeService fridgeService, ProductShoppingListRepository productShoppingListRepository, RecipeService recipeService, ModelMapper modelMapper) {
        this.shoppingListRepository = shoppingListRepository;
        this.fridgeService = fridgeService;
        this.productShoppingListRepository = productShoppingListRepository;
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
    }


    public List<ShoppingListDto> findAllShoppingLists() {
        return shoppingListRepository.findByUserId(fridgeService.getUserId()).stream().map(shoppingList -> modelMapper.
                map(shoppingList, ShoppingListDto.class)).toList();
    }

    @Transactional
    public void saveShoppingList(ShoppingListDto shoppingListDto) {
        ShoppingList shoppingList = modelMapper.map(shoppingListDto, ShoppingList.class);
        if (shoppingList.getShoppingProductList() != null) {
            shoppingList.getShoppingProductList().forEach(x -> x.setShoppingList(shoppingList));
        }
        shoppingList.setUserId(fridgeService.getUserId());
        shoppingListRepository.save(shoppingList);
        log.info("Zapisano listę zakupów: " + shoppingList.getName());

    }

    public void deleteShoppingListById(Long shoppingListId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId).orElseThrow(() -> new RuntimeException("Shopping List not found!"));
        log.info("Usunięto listę zakupów: " + shoppingList.getName());
        shoppingListRepository.deleteShoppingListById(shoppingListId);

    }

    public ShoppingListDto getShoppingList(Long id) {
        ShoppingList shoppingList = shoppingListRepository.findById(id).orElse(new ShoppingList());
        return modelMapper.map(shoppingList, ShoppingListDto.class);
    }

    public Page<ProductShoppingListDto> viewShoppingList(Long id, Pageable pageable) {
        Page<ProductShoppingList> shoppingList = new PageImpl<>(new ArrayList<>());
        addProductsRecipeToShoppingList(id);
        if (!productShoppingListRepository.findProductsInShoppingList(id, pageable).isEmpty())
            shoppingList = productShoppingListRepository.findProductsInShoppingList(id, pageable);
        return shoppingList.map(productShoppingList -> modelMapper.map(productShoppingList, ProductShoppingListDto.class));
    }

    @Transactional
    public void addRecipeToShoppingList(Long recipeId, Long id) {
        Recipe recipe = modelMapper.map(recipeService.getRecipeById(recipeId), Recipe.class);
        ShoppingListDto shoppingListDto = getShoppingList(id);
        ShoppingList shoppingList = modelMapper.map(shoppingListDto, ShoppingList.class);
        List<Recipe> shoppingListRecipe = shoppingList.getShoppingListRecipe();
        if (!shoppingListRecipe.contains(recipe)) {
            shoppingList.addRecipe(recipe);
        }

        shoppingListRepository.save(modelMapper.map(shoppingList, ShoppingList.class));
        log.info("Dodano przepis " + recipe.getName() + " do listy zakupów: " + shoppingList.getName());
    }

    public void addProductsRecipeToShoppingList(Long id) {
        List<ShoppingListDto.RecipeDto> recipeDtoList = getShoppingList(id).getShoppingListRecipe();
        List<ShoppingListDto.ProductShoppingListDto> productShoppingListDtos = compareListOfRecipeAndProductsInFridge(recipeDtoList);

        List<ProductShoppingList> productsFromRecipe = productShoppingListDtos
                .stream().map(productShoppingListDto -> modelMapper.map(productShoppingListDto, ProductShoppingList.class)).toList();
        ShoppingList shoppingList = modelMapper.map(getShoppingList(id), ShoppingList.class);

        deleteProductsFromShoppingList(id);

        shoppingList.setShoppingProductList(productsFromRecipe);
        for (ProductShoppingList list : shoppingList.getShoppingProductList()) {
            list.setShoppingList(shoppingList);
        }
        shoppingListRepository.save(shoppingList);
        log.info("Dodane produkty z przepisu do listy zakupów: " + shoppingList.getName());

    }

    public void deleteProductsFromShoppingList(Long id) {
        shoppingListRepository.deleteProductsFromShoppingList(id);
    }

    public List<ShoppingListDto.ProductShoppingListDto> compareListOfRecipeAndProductsInFridge(List<ShoppingListDto.RecipeDto> recipeListDto) {

        List<FridgeDto.ProductInFridgeDto> productsInFridge = fridgeService.getFridge().getProductsInFridge();

        if (productsInFridge == null) {
            productsInFridge = new ArrayList<>();
        }
        Map<String, Double> fridgeOne = productsInFridge
                .stream().collect(Collectors.toMap(FridgeDto.ProductInFridgeDto::getProductName, FridgeDto.ProductInFridgeDto::getAmount));
        Map<String, Double> fridgeMap = new HashMap<>();
        for (String key : fridgeOne.keySet()) {
            if (fridgeMap.get(key) == null) {
                fridgeMap.putIfAbsent(key, fridgeOne.get(key));
            } else {
                Double value = fridgeMap.get(key) + fridgeOne.get(key);
                fridgeMap.put(key, value);
            }
        }

        Map<String, Double> recipeMap = new HashMap<>();
        Map<String, ProductUnit> unitsMap = new HashMap<>();
        List<ShoppingListDto.ProductShoppingListDto> shoppingList = new ArrayList<>();
        convertToMapAndCompareProducts(recipeListDto, recipeMap, unitsMap);
        Map<String, Double> map = hashMapDifference(fridgeMap, recipeMap);
        addProductsToShoppingList(unitsMap, shoppingList, map);
        return shoppingList;
    }

    private static void addProductsToShoppingList(Map<String, ProductUnit> unitsMap, List<ShoppingListDto.ProductShoppingListDto> shoppingList, Map<String, Double> map) {

        for (String key : map.keySet()) {
            ShoppingListDto.ProductShoppingListDto product = new ShoppingListDto.ProductShoppingListDto();
            product.setProductName(key);
            product.setAmount(map.get(key));
            product.setUnit(unitsMap.get(key));
            shoppingList.add(product);
        }
    }

    private Map<String, Double> convertToMapAndCompareProducts(List<ShoppingListDto.RecipeDto> recipeListDto, Map<String, Double> recipeMap,
                                                               Map<String, ProductUnit> unitsMap) {
        List<Recipe> recipeList = recipeListDto
                .stream().map(recipeDto -> modelMapper.map(recipeDto, Recipe.class)).toList();

        for (Recipe recipe : recipeList) {
            Map<String, Double> recipeOne = recipe.getProductList().stream().collect(Collectors.toMap(ProductRecipe::getProductName, ProductRecipe::getAmount));
            Map<String, ProductUnit> recipeUnits = recipe.getProductList().stream().collect(Collectors.toMap(ProductRecipe::getProductName, ProductRecipe::getUnit));
            for (String key : recipeOne.keySet()) {
                if (recipeMap.get(key) == null) {
                    recipeMap.putIfAbsent(key, recipeOne.get(key));
                    unitsMap.putIfAbsent(key, recipeUnits.get(key));
                } else {
                    Double value = recipeMap.get(key) + recipeOne.get(key);
                    recipeMap.put(key, value);
                }
            }
        }
        return recipeMap;
    }


    public Map<String, Double> hashMapDifference(Map<String, Double> a, Map<String, Double> b) {
        Iterator<String> bKeyIterator = b.keySet().iterator();
        String key;
        Double value;
        Map<String, Double> difference = new HashMap<>();

        while (bKeyIterator.hasNext()) {
            key = bKeyIterator.next();
            if (a.get(key) == null) {
                value = b.get(key);
            } else {
                value = b.get(key) - a.get(key);
            }
            if (value > 0) {
                difference.put(key, value);
            }
        }
        return difference;
    }

}