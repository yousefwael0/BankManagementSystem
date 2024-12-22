package models.account;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private LocalDateTime date;
    private String type;
    private double amount;
    public String clientId;
    private static int counter = 1;

    // Constructor
    public Transaction(LocalDateTime date, String type, double amount, String clientId) {

        this.transactionId = "T" + String.format("%03d", counter++);
        this.setDate(date);
        this.setType(type);
        this.setAmount(amount);
        this.clientId = clientId;
    }
    // constructor def
    public Transaction() {
        this.transactionId = "T" + String.format("%03d", counter++);
        this.date = LocalDateTime.now();
        this.type = null;
        this.amount = 0;
        this.clientId = null;
    }

    // Getter Setter
    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Transaction date cannot be null.");
        }
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Transaction type cannot be null or empty.");
        }
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative.");
        }
        this.amount = amount;
    }

    public static void setCounter(int counter) {
        Transaction.counter = counter;
    }
    public static int getCounter() {return counter;}

   //byban keda fel gui
    @Override
    public String toString() {
        return "Transaction Details:\n" +
                "Transaction ID: " + transactionId + "\n" +
                "Client ID: " + clientId + "\n" +
                "Date: " + date + "\n" +
                "Type: " + type + "\n" +
                "Amount: " + amount + "\n";
    }
}





