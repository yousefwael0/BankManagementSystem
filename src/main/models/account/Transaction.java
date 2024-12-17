package models.account;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private LocalDateTime date;
    private String type;
    private double amount;
    private String employeeId;
    private static int counter = 1; // Counter made static to ensure it increments globally

    // Constructor
    public Transaction(LocalDateTime date, String type, double amount, String employeeId) {

        this.transactionId = Integer.toString(counter++);
        this.setDate(date);
        this.setType(type);
        this.setAmount(amount);
        this.setEmployeeId(employeeId);
    }

    // Getter and Setter methods
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty.");
        }
        this.employeeId = employeeId;
    }

    public static void setCounter(int counter) {
        Transaction.counter = counter;
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





