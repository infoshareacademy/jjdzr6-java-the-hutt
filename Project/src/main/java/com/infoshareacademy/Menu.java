package com.infoshareacademy.model;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    Scanner scanner1 = new Scanner(System.in);

    private int chooseMainNumber;
    private String chooseFunction;

    public void mainMenu() {

        do {
            printMenu();
            chooseMainNumber = scanner.nextInt();
            switch (chooseMainNumber) {
                case 1:
                    goTo1Menu();
                    chooseFunction = scanner1.nextLine();
                    if (chooseFunction.equals("0")) { //code will be complete after add functionality
                        chooseMainNumber = -2;
                    }
                    break;
                case 2:
                    goTo2Menu();
                    chooseFunction = scanner1.nextLine();
                    if (chooseFunction.equals("0")) { //code will be complete after add functionality
                        chooseMainNumber = -2;
                    }
                    break;
                case 3:
                    goTo3Menu();
                    chooseFunction = scanner1.nextLine();
                    if (chooseFunction.equals("0")) { //code will be complete after add functionality
                        chooseMainNumber = -2;
                    }
                    break;
                case 4:
                    goTo4Menu();
                    chooseFunction = scanner1.nextLine();
                    if (chooseFunction.equals("0")) { //code will be complete after add functionality
                        chooseMainNumber = -2;
                    }
                    break;
                case 5:
                    goTo5Menu();
                    chooseFunction = scanner1.nextLine();
                    if (chooseFunction.equals("0")) { //code will be complete after add functionality
                        chooseMainNumber = -2;
                    }
                    break;
                case 0:
                    goTo0Menu();
                    chooseFunction = scanner1.nextLine();
                    if (chooseFunction.toUpperCase().equals("T")) {
                        break;
                    } else if (chooseFunction.toUpperCase().equals("N")) {
                        chooseMainNumber = -2;
                    }
                    break;
                default:
                    System.out.println("Niepoprawny numer. Wybierz opcje 0-5.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
            }
        } while (chooseMainNumber < -1 || chooseMainNumber > 5);

    }

    public void printMenu() {
        System.out.println("======================================");
        System.out.println("          Witaj w aplikacji");
        System.out.println("                Fridge");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("             Menu główne");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Wybierz jedną z ponizszych opcji:");
        System.out.println("");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("1. Preferencje żywieniowe użytkownika.");
        System.out.println("2. Zawartość lodówki.");
        System.out.println("3. Przepisownik.");
        System.out.println("4. Lista zakupów.");
        System.out.println("5. Generuj (na podstawie produktów).");
        System.out.println("0. Zamknij aplikację.");
        System.out.println("");
        System.out.println("=====================================");
    }

    public void goTo0Menu() {
        System.out.println("======================================");
        System.out.println("Czy napewno chcesz opuścić program?");
        System.out.println("[T]ak/[N]ie");
        System.out.println("======================================");
    }

    public void goTo1Menu() {
        System.out.println("======================================");
        System.out.println("  Preferencje żywieniowe użytkownika");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("");
        System.out.println("0. Powrót do menu głównego.");
        System.out.println("");
        System.out.println("======================================");
    }

    public void goTo2Menu() {
        System.out.println("======================================");
        System.out.println("          Zawartość lodówki");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("");
        System.out.println("1. Lista produktów.");
        System.out.println("2. Dodaj produkt.");
        System.out.println("3. Usuń produkt.");
        System.out.println("0. Powrót do menu głównego.");
        System.out.println("");
        System.out.println("======================================");
    }

    public void goTo3Menu() {
        System.out.println("======================================");
        System.out.println("            Przepisownik");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("");
        System.out.println("1. Wyszukaj przepis.");
        System.out.println("2. Dodaj przepis.");
        System.out.println("3. Usuń przepis.");
        System.out.println("0. Powrót do menu głównego.");
        System.out.println("");
        System.out.println("======================================");
    }

    public void goTo4Menu() {
        System.out.println("======================================");
        System.out.println("            Lista zakupów");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("");
        System.out.println("0. Powrót do menu głównego.");
        System.out.println("");
        System.out.println("======================================");
    }

    public void goTo5Menu() {
        System.out.println("======================================");
        System.out.println("   Generuj (na podstawie produktów)");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("");
        System.out.println("1. Pojedynczy przepis.");
        System.out.println("2. Śniadania.");
        System.out.println("3. Obiady.");
        System.out.println("4. Kolacje.");
        System.out.println("5. Generuj dietę na wybraną ilość dni.");
        System.out.println("0. Powrót do menu głównego.");
        System.out.println("");
        System.out.println("======================================");
    }

}


