package models.account;
import models.user.Client;

public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double balance, double interestRate, Client client)
            throws IllegalArgumentException {
        super(accountNumber, "Savings", balance, interestRate, client);
    }

    public double calculateInterest() {
        return getBalance() * getInterestRate();
    }

    public void applyInterest() throws IllegalArgumentException{
        double interest = calculateInterest();
        deposit(interest);
    }
}
