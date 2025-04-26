package main.java.interfaces;

import main.java.models.Account;
import main.java.models.AccountType;

public interface BankInterface {
    String getName();
    Account createAccount(String ownerName, double initialDeposit, String pin, AccountType type);
    Account authenticate(String accountNumber, String pin);
    boolean transferFunds(Account source, String destinationAccountNumber, double amount);
}