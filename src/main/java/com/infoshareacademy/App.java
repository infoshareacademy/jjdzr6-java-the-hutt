package com.infoshareacademy;

import com.infoshareacademy.other.Menu;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Team name: Java The Hutt");
        Menu menu = new Menu();
        menu.mainMenu();
    }
}
