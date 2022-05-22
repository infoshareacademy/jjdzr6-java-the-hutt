package com.infoshareacademy;

import java.util.InputMismatchException;
import java.util.Scanner;
//clearowanie menu po przejsciu do kolejnej funkcji <- dac znac czy poszlo
public class Menu {

    Scanner scanner = new Scanner(System.in);

    private boolean flagMenu = true;
    public void mainMenu() {

        int chooseMainNumber;
        do {
            printMenu();
            try {
                chooseMainNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("");
                scanner.nextLine();
                continue;
            }

            switch (chooseMainNumber) {
                case 1:
                    goTo1Menu();
                    int chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 2:
                    goTo2Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 3:
                    goTo3Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 4:
                    goTo4Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 5:
                    goTo5Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 0:
                    goTo0Menu();
                    scanner = new Scanner(System.in);
                    String chooseExit = scanner.nextLine();
                    if (chooseExit.toUpperCase().equals("T")) {
                        flagMenu = false;
                    } else if (chooseExit.toUpperCase().equals("N")) {
                        continue;
                    }
                    break;
                default:
                    System.out.println("Niepoprawny numer. Wybierz opcje 0-5.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
            }
        } while (flagMenu);

    }

    public void printMenu() {
        System.out.println("======================================");
        System.out.println("          Witaj w aplikacji");
        System.out.println("                Fridge");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("             Menu główne");
        System.out.println("======================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("Wybierz jedną z ponizszych opcji:");
        System.out.println("");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
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
        } catch (InterruptedException ignored) {
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
        } catch (InterruptedException ignored) {
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
        } catch (InterruptedException ignored) {
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
        } catch (InterruptedException ignored) {
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
        } catch (InterruptedException ignored) {
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

