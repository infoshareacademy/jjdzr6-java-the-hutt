package com.infoshareacademy;

import com.infoshareacademy.product.Product;
import com.infoshareacademy.shopping_list.ShoppingList;
import com.infoshareacademy.shopping_list.ShoppingListMethods;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main( String[] args ) throws IOException {
        System.out.println( "Team name: Java The Hutt" );
        Menu menu = new Menu();
        menu.mainMenu();

    }
}
