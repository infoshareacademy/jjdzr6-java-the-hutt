package com.infoshareacademy.service;


import com.infoshareacademy.DTO.ShoppingListDto;
import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.shopping_list.ShoppingList;
import com.infoshareacademy.repository.ShoppingListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final FridgeService fridgeService;

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;
    private static Logger LOGGER = LogManager.getLogger(ShoppingListService.class.getName());




    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository, FridgeService fridgeService, RecipeService recipeService, ModelMapper modelMapper) {
        this.shoppingListRepository = shoppingListRepository;
        this.fridgeService = fridgeService;
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
    }



    public List<ShoppingListDto> findAllShoppingLists() {
        return shoppingListRepository.findByUserId(fridgeService.getDEFAULT_FRIDGE_ID()).stream().map(shoppingList -> modelMapper.
                map(shoppingList, ShoppingListDto.class)).toList();
    }

    @Transactional
    public void saveShoppingList(ShoppingListDto shoppingListDto) {
        ShoppingList shoppingList = modelMapper.map(shoppingListDto, ShoppingList.class);
        if (shoppingList.getShoppingProductList() != null) {
            shoppingList.getShoppingProductList().forEach(x -> x.setShoppingList(shoppingList));
        }
        shoppingList.setUserId(fridgeService.getDEFAULT_FRIDGE_ID());
        shoppingListRepository.save(shoppingList);
        LOGGER.info("Zapisano listę zakupów: " + shoppingList.getName());

    }

    public void deleteShoppingListById(Long shoppingListId) {
        LOGGER.info("Usunięto listę zakupów: " + shoppingListRepository.findById(shoppingListId).get().getName());
        shoppingListRepository.deleteShoppingListById(shoppingListId);

    }

    public ShoppingListDto getShoppingList(Long id) {
        ShoppingList shoppingList;
        if (shoppingListRepository.findById(id).isPresent()) {
            shoppingList = shoppingListRepository.findById(id).get();
        } else {
            shoppingList = new ShoppingList();
        }
        return modelMapper.map(shoppingList, ShoppingListDto.class);
    }

    public ShoppingListDto viewShoppingList(Long id) {
        ShoppingList shoppingList = new ShoppingList();
        addProductsFromRecipesToShoppingList(id);
        if (shoppingListRepository.findById(id).isPresent()) shoppingList = shoppingListRepository.findById(id).get();
        return modelMapper.map(shoppingList, ShoppingListDto.class);
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

        LOGGER.info("Dodano przepis " + recipe.getName() + " do listy zakupów: " + shoppingList.getName());
    }

    public void addProductsFromRecipesToShoppingList(Long id) {
        List<ShoppingListDto.RecipeDto> recipeDtoList = getShoppingList(id).getShoppingListRecipe();
        List<ShoppingListDto.ProductShoppingListDto> productShoppingListDtos = compareListOfRecipeAndProductsFromFridge(recipeDtoList);
        List<ProductShoppingList> productsFromRecipe = productShoppingListDtos.stream().map(productShoppingListDto -> modelMapper.map(productShoppingListDto, ProductShoppingList.class)).toList();
        ShoppingList shoppingList = modelMapper.map(getShoppingList(id), ShoppingList.class);
        deleteProductsFromShoppingList(id);
        shoppingList.setShoppingProductList(productsFromRecipe);
        for (ProductShoppingList list : shoppingList.getShoppingProductList()) {
            list.setShoppingList(shoppingList);
        }
        shoppingListRepository.save(shoppingList);
        LOGGER.info("Dodane produkty z przepisu do listy zakupów: " + shoppingList.getName());

    }

    public void deleteProductsFromShoppingList(Long id) {
        shoppingListRepository.deleteProductsFromShoppingList(id);
    }

    public List<ShoppingListDto.ProductShoppingListDto> compareListOfRecipeAndProductsFromFridge(List<ShoppingListDto.RecipeDto> recipeListDto) {

        List<FridgeDto.ProductInFridgeDto> productsInFridge = fridgeService.getFridge().getProductsInFridge();

        if(productsInFridge == null){
            productsInFridge = new ArrayList<>();
        }
        Map<String, Double> fridgeOne = productsInFridge.stream().collect(Collectors.toMap(FridgeDto.ProductInFridgeDto::getProductName, FridgeDto.ProductInFridgeDto::getAmount));
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
/*        List<RecipeDto> recipeDtoStream = recipeListDto.stream().map(recipeDto -> modelMapper.map(recipeDto, RecipeDto.class)).toList();*/
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

    private Map<String, Double> convertToMapAndCompareProducts(List<ShoppingListDto.RecipeDto> recipeListDto, Map<String, Double> recipeMap, Map<String, ProductUnit> unitsMap) {
        List<Recipe> recipeList = recipeListDto.stream().map(recipeDto -> modelMapper.map(recipeDto, Recipe.class)).toList();
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