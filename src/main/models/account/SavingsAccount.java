package models.account;
import models.user.Client;

public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double balance, double interestRate, String clientId)
            throws IllegalArgumentException {
        super(accountNumber, "SAVINGS", balance, interestRate, clientId);
    }

    public double calculateInterest() {
        return getBalance() * getInterestRate();
    }

    public void applyInterest() throws IllegalArgumentException{
        double interest = calculateInterest();
        deposit(interest);
    }
}
