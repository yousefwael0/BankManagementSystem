package models.account;

import models.user.Client;

public class CreditCard {
    public final String cardNumber;
    private double limit = 20000;  // Fixed amount for the card
    private String status = "Active";  // Card status (Active/Disabled)
    private Account account;  // Reference to the client owning this card

    public CreditCard(String cardNumber, Client client) {
        this.cardNumber = cardNumber;
        this.account = account;
    }

    public void makePayment(double amount) {
        if (amount <= limit) {
            this.limit -= amount;
            System.out.println("Payment of " + amount + " successful.");
            // Add loyalty points for the payment made
            account.getClient().earnLoyaltyPoints((int) amount / 10); // Example: 1 point for every 10 LE spent
        } else {
            System.out.println("Payment exceeds card limit.");
        }
    }

    public void disableCard() {
        this.status = "Disabled";
        System.out.println("Credit card disabled.");
    }
}