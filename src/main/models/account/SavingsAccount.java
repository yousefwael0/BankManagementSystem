package models.account;

import models.user.Client;

public class SavingsAccount extends Account {
    public SavingsAccount(double balance, double interestRate, String clientId)
            throws IllegalArgumentException {
        super("SAVINGS", balance, interestRate, clientId, null);
    }
    public SavingsAccount(){
        super();
    }

    public double calculateInterest() {
        return getBalance() * getInterestRate();
    }

    public void applyInterest() throws IllegalArgumentException{
        double interest = calculateInterest();
        deposit(interest);
    }
}
