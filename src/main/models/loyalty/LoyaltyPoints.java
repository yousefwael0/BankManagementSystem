package models.loyalty;

import models.user.Client;

public class LoyaltyPoints {
    private int points;
    public Client client;

    public LoyaltyPoints(Client client) {
        this.client = client;
        this.points = 0;  // Start with 0 points
    }
    public LoyaltyPoints() {
        this.points = 0;
        this.client = null;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int additionalPoints) {
        this.points += additionalPoints;
    }

    public void redeemPoints(int pointsToRedeem) {
        if (pointsToRedeem <= points) {
            this.points -= pointsToRedeem;
        } else {
            throw new IllegalArgumentException("Not enough points to redeem.\nPoints Reached: " + points);
        }
    }

    @Override
    public String toString() {
        return "Client ID: " + client.userId + ", Loyalty Points: " + points;
    }
}