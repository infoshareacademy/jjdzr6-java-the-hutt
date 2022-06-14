package com.infoshareacademy;

import com.infoshareacademy.product.Product;
import com.infoshareacademy.shopping_list.ShoppingList;
import com.infoshareacademy.service.ShoppingListService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main( String[] args ) throws IOException {
//        System.out.println( "Team name: Java The Hutt" );
//        Menu menu = new Menu();
//        menu.mainMenu();

        Product product = new Product("jabłka");
        Product product2 = new Product("jajka");
        ShoppingListService shoppingListService = new ShoppingListService();
        Map<String, Object>map = new HashMap<String, Object>();
        map.put(product.getName(), Double.valueOf(3));
        map.put(product2.getName(), Double.valueOf(3));
        ShoppingList shoppingList = new ShoppingList(map);
        shoppingListService.writeJson(shoppingList);
        shoppingListService.getJson();

    }
}
