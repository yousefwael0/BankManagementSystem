package models.account;

import java.util.Date;

public class Transaction {
    private String transactionID;
    private double amount;
    private String type; // Deposit, Withdrawal, Transfer
    private Date date;
    private String clientID;
    private String empID;

    public Transaction(String transactionID, double amount, String type, Date date, String clientID, String empID) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.clientID = clientID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public String getClientID() {
        return clientID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}