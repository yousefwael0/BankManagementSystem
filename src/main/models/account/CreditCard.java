package models.account;


public class CreditCard {
    public final String cardNumber;
    private double limit=20000;
    private boolean isActive;
    public final String accountNumber;
    public final String clientId;
    private static int counter = 1;
    //private Account account

    public CreditCard(String accountNumber, String clientId, boolean isActive) {
        this.cardNumber = "CC" + String.format("%03d", counter++);
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        this.isActive = isActive;
    }

    public double getLimit() {
        return limit;
    }

    public void makePayment(double amount) {

        //Check if Card is active and amount is less than limit
        if (isActive && amount <= limit) {
            System.out.println("Payment Successful :)");
            limit -= amount;
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
        isActive= false;
        System.out.println("Card disabled");
    }

    public void activateCard(){
        isActive= true;
        System.out.println("Card activated");
    }
}