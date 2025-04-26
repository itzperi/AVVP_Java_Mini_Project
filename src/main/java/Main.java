package main.java;

import main.java.interfaces.BankInterface;
import main.java.interfaces.BankUI;
import main.java.services.CommercialBank;
import main.java.ui.ConsoleUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BankInterface bank = new CommercialBank("MyBank");


        Scanner scanner = new Scanner(System.in);


        BankUI userInterface = new ConsoleUI(bank, scanner);
        userInterface.start();


        scanner.close();
    }
}