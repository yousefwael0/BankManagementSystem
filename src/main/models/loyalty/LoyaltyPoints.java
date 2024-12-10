package models.loyalty;

public class LoyaltyPoints {
    private int points;
    private int clientID;

    public LoyaltyPoints(int clientID) {
        this.clientID = clientID;
        this.points = 0;  // Start with 0 points
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
            System.out.println("Not enough points to redeem.");
        }
    }

    public int getClientID() {
        return clientID;
    }

    @Override
    public String toString() {
        return "Client ID: " + clientID + ", Loyalty Points: " + points;
    }
}