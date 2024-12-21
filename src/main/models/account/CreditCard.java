package models.account;


import models.user.Client;

public class CreditCard {
    public String cardNumber;
    private double limit=20000;
    private boolean isActive;
    public String accountNumber;
    public Client client;
    private static int counter = 1;

    public CreditCard(String accountNumber, Client client, boolean isActive) {
        this.cardNumber = "CC" + String.format("%03d", counter++);
        if (accountNumber.isEmpty()|| client==null) {
            throw new IllegalArgumentException("AccountNumber and Client cannot be empty");
        }
        this.accountNumber = accountNumber;
        this.client = client;
        this.isActive = isActive;
    }

    public CreditCard() {
        this.cardNumber = "CC" + String.format("%03d", counter++);
        this.accountNumber = "ACCOUNT NUMBER";
        this.client = null;
        this.isActive = false;
    }

    public double getLimit() {
        return limit;
    }

    public void makePayment(double amount) {

        //Check if Card is active and amount is less than limit
        if (isActive && amount <= limit) {
            limit -= amount;
            client.earnLoyaltyPoints((int) Math.round(amount * 0.25));
        } else if (!isActive) {
            throw new IllegalArgumentException("Card is not active!");
        } else if (amount > limit) {
            throw new IllegalArgumentException("Amount exceeds limit!");
        }
    }

    //Resetting limit
    public void resetLimit() {
        limit=20000;
    }

    public void disableCard(String cardNumber){
        if (isActive){
            isActive=false;
        }else {
            throw new IllegalArgumentException("Card already disabled");
        }
    }

    public void activateCard(){
        if (!isActive){
            isActive=true;
        }else {
            throw new IllegalArgumentException("Card is already active");
        }
    }
}