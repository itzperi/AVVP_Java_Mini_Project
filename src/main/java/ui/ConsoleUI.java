package main.java.ui;

import main.java.interfaces.BankInterface;
import main.java.interfaces.BankUI;
import main.java.models.*;

import java.util.Scanner;
import java.util.List;
import java.text.SimpleDateFormat;

public class ConsoleUI implements BankUI {
    private final BankInterface bank;
    private final Scanner scanner;

    public ConsoleUI(BankInterface bank, Scanner scanner) {
        this.bank = bank;
        this.scanner = scanner;
    }

    @Override
    public void start() {
        boolean exit = false;

        displayMessage("Welcome to " + bank.getName() + " Management System");

        while (!exit) {
            displayMessage("\nPlease select an option:");
            displayMessage("1. Create a new account");
            displayMessage("2. Login to account");
            displayMessage("3. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    loginToAccount();
                    break;
                case 3:
                    exit = true;
                    displayMessage("Thank you for using " + bank.getName() + " Management System.");
                    break;
                default:
                    displayMessage("Invalid option. Please try again.");
            }
        }
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    private void createAccount() {
        displayMessage("\n=== Create New Account ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Create a PIN for your account: ");
        String pin = scanner.nextLine();

        displayMessage("Select account type:");
        displayMessage("1. Savings Account");
        displayMessage("2. Checking Account");
        displayMessage("3. Fixed Deposit Account");
        System.out.print("Your choice: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        AccountType type;
        switch (typeChoice) {
            case 1:
                type = AccountType.SAVINGS;
                break;
            case 2:
                type = AccountType.CHECKING;
                break;
            case 3:
                type = AccountType.FIXED_DEPOSIT;
                break;
            default:
                displayMessage("Invalid choice. Creating Savings account by default.");
                type = AccountType.SAVINGS;
        }

        Account account = bank.createAccount(name, initialDeposit, pin, type);
        displayMessage("\nAccount created successfully!");
        displayMessage("Your account number is: " + account.getAccountNumber());
        displayMessage("Account type: " + account.getAccountType());
    }

    private void loginToAccount() {
        displayMessage("\n=== Login to Account ===");
        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        Account account = bank.authenticate(accountNumber, pin);

        if (account != null) {
            displayMessage("\nLogin successful. Welcome, " + account.getOwnerName() + "!");
            displayMessage("Account Type: " + account.getAccountType());
            handleAccountMenu(account);
        } else {
            displayMessage("Invalid account number or PIN. Please try again.");
        }
    }

    private void handleAccountMenu(Account account) {
        boolean logout = false;

        while (!logout) {
            displayMessage("\n=== Account Menu ===");
            displayMessage("1. Check Balance");
            displayMessage("2. Deposit");
            displayMessage("3. Withdraw");
            displayMessage("4. Transfer Money");
            displayMessage("5. View Transaction History");

            // Special operations based on account type
            if (account instanceof SavingsAccount) {
                displayMessage("6. Apply Interest (Savings Account)");
            } else if (account instanceof FixedDepositAccount) {
                displayMessage("6. Check Maturity Date (Fixed Deposit)");
            }

            displayMessage("9. Logout");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayMessage("\nCurrent Balance: $" + String.format("%.2f", account.getBalance()));
                    break;
                case 2:
                    System.out.print("\nEnter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    account.deposit(depositAmount);
                    displayMessage("Deposit successful. New balance: $" + String.format("%.2f", account.getBalance()));
                    break;
                case 3:
                    System.out.print("\nEnter withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    boolean success = account.withdraw(withdrawAmount);
                    if (success) {
                        displayMessage("Withdrawal successful. New balance: $" + String.format("%.2f", account.getBalance()));
                    } else {
                        displayMessage("Withdrawal failed. Please check your balance or account restrictions.");
                        displayMessage("Current balance: $" + String.format("%.2f", account.getBalance()));
                    }
                    break;
                case 4:
                    transferMoney(account);
                    break;
                case 5:
                    displayMessage("\n=== Transaction History ===");
                    List<Transaction> transactions = account.getTransactionHistory();
                    if (transactions.isEmpty()) {
                        displayMessage("No transactions found.");
                    } else {
                        for (Transaction transaction : transactions) {
                            displayMessage(transaction.toString());
                        }
                    }
                    break;
                case 6:
                    if (account instanceof SavingsAccount) {
                        SavingsAccount savingsAccount = (SavingsAccount) account;
                        savingsAccount.applyInterest();
                        displayMessage("Interest applied. New balance: $" + String.format("%.2f", account.getBalance()));
                    } else if (account instanceof FixedDepositAccount) {
                        FixedDepositAccount fdAccount = (FixedDepositAccount) account;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        displayMessage("Maturity Date: " + sdf.format(fdAccount.getMaturityDate()));
                    } else {
                        displayMessage("Invalid option for this account type.");
                    }
                    break;
                case 9:
                    logout = true;
                    displayMessage("\nYou have been logged out.");
                    break;
                default:
                    displayMessage("Invalid option. Please try again.");
            }
        }
    }

    private void transferMoney(Account sourceAccount) {
        displayMessage("\n=== Transfer Money ===");
        System.out.print("Enter destination account number: ");
        String destinationAccountNumber = scanner.nextLine();

        System.out.print("Enter transfer amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        boolean success = bank.transferFunds(sourceAccount, destinationAccountNumber, amount);

        if (success) {
            displayMessage("Transfer successful. New balance: $" + String.format("%.2f", sourceAccount.getBalance()));
        } else {
            displayMessage("Transfer failed. Please check the account number and your balance.");
        }
    }
}
