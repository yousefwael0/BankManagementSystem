package models.loyalty;

public class LoyaltyPoints {
    private int points;
    private String clientId;

    public LoyaltyPoints(String clientId) {
        this.clientId = clientId;
        this.points = 0;  // Start with 0 points
    }
    public LoyaltyPoints() {
        this.points = 0;
        this.clientId = null;
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
        return "Client ID: " + clientId + ", Loyalty Points: " + points;
    }
}