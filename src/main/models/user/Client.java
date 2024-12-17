package models.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.account.Account;
import models.account.Transaction;
import models.loyalty.LoyaltyPoints;
import java.util.Map;

public class Client extends User{
    // Attributes
    private String phoneNumber;
    private final List<Account> accounts;
    private final LoyaltyPoints loyaltyPoints;
    private final List<Transaction> transactions;

    // Constructor
    public Client(String firstName, String lastName, String username, String password, String phoneNumber) {
        super(firstName, lastName, username, password);
        this.setPhoneNumber(phoneNumber);
        this.accounts = new ArrayList<>();
        this.loyaltyPoints = new LoyaltyPoints(userId); // Initialize loyalty points
        this.transactions = new ArrayList<>();
    }

    // Getters / Setters
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^(01[0125]\\d{8}|02\\d{8}|03\\d{7,8})$")) {
            throw new IllegalArgumentException("Please enter a valid Egyptian phone number.");
        }
        this.phoneNumber = phoneNumber;
    }
    public List<Transaction> getTransactionHistory(){return transactions;}
    public void addTransaction(Transaction transaction){
        for (Transaction trans : transactions){
            if (trans.getTransactionId().equals(transaction.getTransactionId())){
                throw new IllegalArgumentException("Transaction Id must be unique");
            }
        }
        transactions.add(transaction);
    }
    public List<Transaction> getTransactionsByDate(LocalDate date) {
        List<Transaction> transactionsByDate = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getDate().toLocalDate().equals(date)) {
                transactionsByDate.add(transaction);
            }
        }
        return transactionsByDate;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
    public LoyaltyPoints getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Methods
    public void earnLoyaltyPoints(int amount) {loyaltyPoints.addPoints(amount);}
    public void addAccount(Account account) {
        for (Account acc : accounts){
            if (account.accountNumber.equals(acc.accountNumber)){
                throw new IllegalArgumentException("Account already exists.");
            }
        }
        accounts.add(account);
    }

    @Override
    public void editUserInfo(Map<String, String> changes) {
        // Update any field provided in the changes map
        if (changes.containsKey("firstName")) {
            this.setFirstName(changes.get("firstName"));
        }
        if (changes.containsKey("lastName")) {
            this.setLastName(changes.get("lastName"));
        }
        if (changes.containsKey("username")) {
            this.setUsername(changes.get("username"));
        }
        if (changes.containsKey("password")) {
            this.setPassword(changes.get("password"));
        }
        if (changes.containsKey("phoneNumber")) {
            this.setPhoneNumber(changes.get("phoneNumber"));
        }
    }
    @Override
    public String toString() {
        return  "clientID = '" + userId + '\'' + ", phoneNumber = '" + phoneNumber;
    }
}