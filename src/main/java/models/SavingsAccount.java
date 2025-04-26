package main.java.models;

import java.util.Date;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.03; //
    private static final double MINIMUM_BALANCE = 100.0;

    public SavingsAccount(String accountNumber, String ownerName, double initialDeposit, String pin) {
        super(accountNumber, ownerName, initialDeposit, pin, AccountType.SAVINGS);
    }

    @Override
    public boolean withdraw(double amount) {
        return processWithdrawal(amount);
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return amount > 0 && balance - amount >= MINIMUM_BALANCE;
    }

    public void applyInterest() {
        double interest = balance * INTEREST_RATE / 12; // Monthly interest
        balance += interest;
        addTransaction(new Transaction("Interest Credit", interest, balance));
    }
}