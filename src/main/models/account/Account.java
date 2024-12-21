package models.account;


import models.user.Client;

public abstract class Account {
    public String accountNumber;
    public String accountType; // Savings or Current
    private double balance;
    private String status; // Active or Closed
    private double interestRate;
    public Client client;
    private CreditCard creditCard;
    private static int counter = 1;

    // Constructors
    public Account(String accountType, double balance,double interestRate, Client client, CreditCard creditCard)  throws IllegalArgumentException {
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be empty");
        }
        this.accountNumber = generateAccountNumber();
        this.accountType=accountType;
        this.balance = balance;
        this.status ="Active";
        this.interestRate = interestRate;
        this.client = client;
        this.creditCard = creditCard;
    }
    public Account() {
        this.accountNumber = generateAccountNumber();
        this.balance = 0;
        this.status = "Active";
        this.interestRate = 0;
        this.client = null;
        this.creditCard = null;
        this.accountType = "ACCOUNT TYPE";
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

    private void checkAccountActive() throws IllegalArgumentException {
        if ("Closed".equals(status)) {
            throw new IllegalArgumentException("Cannot perform operations on a closed account");
        }
    }

    public void checkAccountStatus() throws IllegalArgumentException{
        checkAccountActive();
        System.out.println("Current account status: " + status);
    }

    // Methods
    public void deposit(double amount) throws IllegalArgumentException {
        checkAccountActive();

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        balance += amount;
    }

    public void withdraw(double amount) throws IllegalArgumentException {
        checkAccountActive();

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (amount > balance) {
            throw new IllegalArgumentException(
                    String.format("Insufficient balance. Available: %.2f, Requested: %.2f", balance, amount)
            );
        }

        balance -= amount;
    }

    public void closeAccount() throws IllegalArgumentException {
        if ("Closed".equals(status)) {
            throw new IllegalArgumentException("Account is already closed");
        }
        this.status = "Closed";
    }

    public void openAccount() throws IllegalArgumentException {
        if ("Active".equals(status)) {
            throw new IllegalArgumentException("Account is already active");
        }
        this.status = "Active";
    }

    public void askForCreditCard() throws IllegalArgumentException {
        checkAccountActive();
        if (creditCard != null) {
            throw new IllegalArgumentException("Credit card already exists for this account");
        }
        creditCard = new CreditCard(this.accountNumber, this.client, true);
    }

    public static void setCounter(int counter) {
        Account.counter = counter;
    }

    @Override
    public String toString() {
        return "Account Number: " + this.accountNumber + ", Balance: " + this.balance + ", Status: " + this.status;
    }
    public String generateAccountNumber(){return "ACC" + String.format("%03d", counter++);}
}