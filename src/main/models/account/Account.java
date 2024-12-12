package models.account;

import models.user.Client;


class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class AccountClosedException extends Exception {
    public AccountClosedException(String message) {
        super(message);
    }
}

public abstract class Account {
    public final String accountNumber;
    public String accountType; // Savings or Current
    private double balance;
    private String status; // Active or Closed
    private double interestRate;
    public final Client client;

    // Constructors
    public Account(String accountNumber,String accountType, double balance,double interestRate, Client client)  throws InvalidAmountException {
        if (balance < 0) {
            throw new InvalidAmountException("Initial balance cannot be negative");
        }
        if (interestRate < 0) {
            throw new InvalidAmountException("Interest rate cannot be negative");
        }
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        this.accountNumber = accountNumber;
        this.accountType=accountType;
        this.balance = balance;
        this.status ="Active";
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

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Client getClient() {
        return client;
    }

    private void checkAccountActive() throws AccountClosedException {
        if ("Closed".equals(status)) {
            throw new AccountClosedException("Cannot perform operations on a closed account");
        }
    }

    public void checkAccountStatus() throws AccountClosedException {
        checkAccountActive();
        System.out.println("Current account status: " + status);
    }

    // Methods
    public void deposit(double amount) throws InvalidAmountException, AccountClosedException {
        checkAccountActive();

        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }

        balance += amount;
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException,
            AccountClosedException {
        checkAccountActive();

        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance. Available: %.2f, Requested: %.2f", balance, amount)
            );
        }

        balance -= amount;
    }

    public void closeAccount() throws AccountClosedException {
        if ("Closed".equals(status)) {
            throw new AccountClosedException("Account is already closed");
        }
        this.status = "Closed";
    }

    public void openAccount() throws AccountClosedException {
        if ("Active".equals(status)) {
            throw new AccountClosedException("Account is already active");
        }
        this.status = "Active";
    }


    @Override
    public String toString() {
        return "Account Number: " + this.accountNumber + ", Balance: " + this.balance + ", Status: " + this.status;
    }
}