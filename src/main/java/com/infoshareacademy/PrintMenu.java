package com.infoshareacademy;

public class PrintMenu {
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
