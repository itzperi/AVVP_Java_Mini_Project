package main.java.services;

import main.java.models.*;

public class AccountFactory {
    public Account createAccount(String accountNumber, String ownerName, double initialDeposit, String pin, AccountType type) {
        switch (type) {
            case SAVINGS:
                return new SavingsAccount(accountNumber, ownerName, initialDeposit, pin);
            case CHECKING:
                return new CheckingAccount(accountNumber, ownerName, initialDeposit, pin);
            case FIXED_DEPOSIT:
                return new FixedDepositAccount(accountNumber, ownerName, initialDeposit, pin);
            default:
                throw new IllegalArgumentException("Invalid account type");
        }
    }
}