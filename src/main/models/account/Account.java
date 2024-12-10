package models.account;

public class Account {
    private String accountNumber;
    private String type; // Savings or Current
    private double balance;
    private String status; // Active or Closed
    private double interestRate;

    public Account(String accountNumber, String type, double balance, String status, double interestRate) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = balance;
        this.status = status;
        this.interestRate = interestRate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public double getInterestRate() {
        return interestRate;
    }

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
}