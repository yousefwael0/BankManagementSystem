package models.account;

import java.time.LocalDateTime;

public class Transaction {
    private long transactionId;
    private LocalDateTime date;
    private String type;
    private double amount;
    private String employeeId;
    private int counter=1;
    // Constructor
    public Transaction(long transactionId, LocalDateTime date, String type, double amount, String employeeId) {
        this.transactionId = counter;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.employeeId = employeeId;
        counter++;
    }

    // Getter and Setter methods
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = counter;
        counter++;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    // Method to get transaction details
    public String getTransactionDetails() {
        return "Transaction Details:\n" +
                "Transaction ID: " + transactionId + "\n" +
                "Date: " + date + "\n" +
                "Type: " + type + "\n" +
                "Amount: " + amount + "\n" +
                "Employee ID: " + employeeId;
    }

}

