package main.java.services;

import main.java.interfaces.BankInterface;
import main.java.models.Account;
import main.java.models.AccountType;

import java.util.HashMap;
import java.util.Map;

public class CommercialBank implements BankInterface {
    private String name;
    private Map<String, Account> accounts;
    private int lastAccountNumber;
    private final AccountFactory accountFactory;

    public CommercialBank(String name) {
        this.name = name;
        this.accounts = new HashMap<>();
        this.lastAccountNumber = 1000; // Starting account number
        this.accountFactory = new AccountFactory();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Account createAccount(String ownerName, double initialDeposit, String pin, AccountType type) {
        String accountNumber = generateAccountNumber();
        Account account = accountFactory.createAccount(accountNumber, ownerName, initialDeposit, pin, type);
        accounts.put(accountNumber, account);
        return account;
    }

    @Override
    public Account authenticate(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        if (account != null && account.validatePin(pin)) {
            return account;
        }
        return null;
    }

    @Override
    public boolean transferFunds(Account source, String destinationAccountNumber, double amount) {
        Account destination = accounts.get(destinationAccountNumber);
        if (destination == null) {
            return false;
        }

        if (source.withdraw(amount)) {
            destination.deposit(amount);
            return true;
        }
        return false;
    }

    private String generateAccountNumber() {
        lastAccountNumber++;
        return "ACC" + lastAccountNumber;
    }
}