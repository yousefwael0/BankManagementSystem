package models.account;

import models.user.Client;

public class CreditCard {
    private String cardNumber;
    private double limit = 20000;  // Fixed amount for the card
    private String status = "Active";  // Card status (Active/Disabled)
    private Client client;  // Reference to the client owning this card

    public CreditCard(String cardNumber, Client client) {
        this.cardNumber = cardNumber;
        this.client = client;
    }

    public void makePayment(double amount) {
        if (amount <= limit) {
            this.limit -= amount;
            System.out.println("Payment of " + amount + " successful.");
            // Add loyalty points for the payment made
            client.earnLoyaltyPoints((int) amount / 10); // Example: 1 point for every 10 LE spent
        } else {
            System.out.println("Payment exceeds card limit.");
        }
    }

    public void disableCard() {
        this.status = "Disabled";
        System.out.println("Credit card disabled.");
    }
}