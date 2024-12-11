package models.account;

import models.user.Client;

public class Account {
    public final String accountNumber;
    public final AccountType type; // Savings or Current
    private double balance;
    private String status; // Active or Closed
    private double interestRate;
    public final Client client;

    // Constructors
    public Account(String accountNumber, AccountType type, double balance, String status, double interestRate, Client client) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = balance;
        this.status = status;
        this.interestRate = interestRate;
        this.client = client;
    }

    // Getters / Setters
    public double getBalance() {
        return balance;
    }
    public String getStatus() {
        return status;
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {this.interestRate = interestRate;}
    public Client getClient(){
        return client;
    }


    // Methods
    public void deposit(double amount) {
        this.balance += amount;
    }
    public void withdraw(double amount) {
        if (this.balance - amount >= 0) {
            this.balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }
    public void closeAccount() {
        this.status = "Closed";
    }
    public void openAccount() {
        this.status = "Active";
    }

    @Override
    public String toString() {
        return "Account Number: " + this.accountNumber + ", Balance: " + this.balance + ", Status: " + this.status;
    }
}