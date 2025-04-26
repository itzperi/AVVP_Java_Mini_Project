package main.java.models;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private Date timestamp;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        String formattedAmount = String.format("%.2f", Math.abs(amount));
        String prefix = amount >= 0 ? "+" : "-";
        return DATE_FORMAT.format(timestamp) + " | " + type + ": " + prefix + "$" + formattedAmount +
                " | Balance: $" + String.format("%.2f", balanceAfter);
    }
}
