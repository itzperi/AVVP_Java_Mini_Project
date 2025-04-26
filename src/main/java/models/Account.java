package main.java.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private String ownerName;
    protected double balance;
    private String pin;
    private List<Transaction> transactionHistory;
    protected AccountType type;

    public Account(String accountNumber, String ownerName, double initialDeposit, String pin, AccountType type) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialDeposit;
        this.pin = pin;
        this.type = type;
        this.transactionHistory = new ArrayList<>();

        // Record initial deposit
        if (initialDeposit > 0) {
            transactionHistory.add(new Transaction("Initial Deposit", initialDeposit, balance));
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return type;
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount, balance));
        }
    }

    public abstract boolean withdraw(double amount);

    public final boolean processWithdrawal(double amount) {
        if (canWithdraw(amount)) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", -amount, balance));
            return true;
        }
        return false;
    }

    protected abstract boolean canWithdraw(double amount);

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    protected void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return type + " Account: " + accountNumber + " | Owner: " + ownerName + " | Balance: $" + String.format("%.2f", balance);
    }
}