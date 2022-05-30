package com.infoshareacademy;

import java.util.InputMismatchException;
import java.util.Scanner;
public class Menu {

    PrintMenu printMenu = new PrintMenu();
    Scanner scanner = new Scanner(System.in);
    private boolean flagMenu = true;

    public void mainMenu() {

        int chooseMainNumber;
        do {
            printMenu.printMenu();
            try {
                chooseMainNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("");
                scanner.nextLine();
                continue;
            }

            switch (chooseMainNumber) {
                case 1:
                    printMenu.goTo1Menu();
                    int chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 2:
                    printMenu.goTo2Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 3:
                    printMenu.goTo3Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 4:
                    printMenu.goTo4Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 5:
                    printMenu.goTo5Menu();
                    chooseFunction = scanner.nextInt();
                    if (chooseFunction == 0) { //code will be complete after add functionality
                        continue;
                    }
                    break;
                case 0:
                    printMenu.goTo0Menu();
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
}

