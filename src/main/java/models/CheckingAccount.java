package main.java.models;

public class CheckingAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 500.0;
    private static final double OVERDRAFT_FEE = 25.0;

    public CheckingAccount(String accountNumber, String ownerName, double initialDeposit, String pin) {
        super(accountNumber, ownerName, initialDeposit, pin, AccountType.CHECKING);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }

        if (balance >= amount) {
            return processWithdrawal(amount);
        } else if (balance + OVERDRAFT_LIMIT >= amount) {
            balance -= amount;
            addTransaction(new Transaction("Withdrawal", -amount, balance));

            balance -= OVERDRAFT_FEE;
            addTransaction(new Transaction("Overdraft Fee", -OVERDRAFT_FEE, balance));
            return true;
        }
        return false;
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return amount > 0 && (balance >= amount || balance + OVERDRAFT_LIMIT >= amount);
    }
}
