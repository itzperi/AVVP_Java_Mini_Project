package main.java.models;

import java.util.Date;
import java.text.SimpleDateFormat;

public class FixedDepositAccount extends Account {
    private static final double INTEREST_RATE = 0.05;
    private Date maturityDate;
    private static final long MATURITY_PERIOD_MS = 365L * 24 * 60 * 60 * 1000;

    public FixedDepositAccount(String accountNumber, String ownerName, double initialDeposit, String pin) {
        super(accountNumber, ownerName, initialDeposit, pin, AccountType.FIXED_DEPOSIT);
        this.maturityDate = new Date(System.currentTimeMillis() + MATURITY_PERIOD_MS);
    }

    @Override
    public boolean withdraw(double amount) {
        return processWithdrawal(amount);
    }

    @Override
    protected boolean canWithdraw(double amount) {
        Date currentDate = new Date();
        return amount > 0 && currentDate.after(maturityDate) && balance >= amount;
    }

    public void applyInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        addTransaction(new Transaction("Interest Credit", interest, balance));
    }

    public Date getMaturityDate() {
        return maturityDate;
    }
}