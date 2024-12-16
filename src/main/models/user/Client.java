package models.user;

import java.util.ArrayList;
import java.util.List;
import models.account.Account;
import models.account.Transaction;
import models.loyalty.LoyaltyPoints;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Client extends User{

    // Attributes
    private String phoneNumber;
    private List<Account> accounts;
    private LoyaltyPoints loyaltyPoints;
    //private double balance; in the accounts class
    //private String state; int the accounts class
    //private String type; in the accounts class

/*
    private static final String default_id = "No ID set yet";
    private static final String default_pNumber = "No phone number set yet";
    private static final double default_balance = 0.0;


Clinet's data cannot be empty
    public Client() {
        this.setPhoneNumber("NAN");
        //this.balance = default_balance;
        /*this.state = "Unknown";
        this.type = "Unknown";
    }

    public Client(String phoneNumber){
        //this();
        //this.clientID = (clientID != null) ? clientID : default_id;
        this.phoneNumber = (phoneNumber != null) ? phoneNumber : default_pNumber;
        //this.balance = balance;
        this.state = (state != null) ? state : "unknown";
        //this.type = (type != null) ? type: "unknown";
    }*/

    // Constructor
    public Client(String firstName, String lastName, String username, String password, String phoneNumber) {
        super(firstName, lastName, username, password);
        this.setPhoneNumber(phoneNumber);
        this.accounts = new ArrayList<>();
        this.loyaltyPoints = new LoyaltyPoints(userId);  // Initialize loyalty points
    }

    // Getters / Setters
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^(01[0125]\\d{8}|02\\d{8}|03\\d{7,8})$")) {
            throw new IllegalArgumentException("Please enter a valid Egyptian phone number.");
        }
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

/*    public void setBalance(double balance) {
        if (balance >= 0)
            this.balance = balance;
        else
            System.out.println("Invalid balance amount");
    }
    public double getBalance() {
        return balance;
    }


    public void setType(String type) {
        if (type != null) {
            this.type = type;
        } else {
            System.out.println("Invalid type");
        }
    }
    public String getType() {
        return type;
    }


   public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    public String getClientID() {
        return clientID;
    }
  */

    // Getters / Setters
    public List<Account> getAccounts() {
        return accounts;
    }
    public LoyaltyPoints getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Methods
    /*    public void editPersonalInfo(String newNumber, String newState){

        if(newNumber != null){
            this.phoneNumber = newNumber;
        }
        if (newState != null) {
            this.state = newState;
        }
        System.out.println("Personal information updated.");
    }*/
    @Override
    public void editUserInfo(Map<String, String> changes) {
        // Update any field provided in the changes map
        if (changes.containsKey("firstName")) {
            this.firstName = changes.get("firstName");
        }
        if (changes.containsKey("lastName")) {
            this.lastName = changes.get("lastName");
        }
        if (changes.containsKey("username")) {
            this.username = changes.get("username");
        }
        if (changes.containsKey("password")) {
            this.password = changes.get("password");
        }
        if (changes.containsKey("phoneNumber")) {
            this.setPhoneNumber(changes.get("phoneNumber"));
        }
    }
    @Override
    public String toString() {
        return  "clientID = '" + userId + '\'' + ", phoneNumber = '" + phoneNumber;
    }

    public void showTransactionHistory(List<Transaction> transactions){
        for (Transaction transaction : transactions) {
            if (transaction.getClientID().equals(this.userId)) {
                System.out.println(transaction);
            }
        }
    }
    public void earnLoyaltyPoints(int amount) {loyaltyPoints.addPoints(amount);}
    public void addAccount(Account account) {this.accounts.add(account);}

/* in the bank class (Deposit, withdraw and transfer)
    public boolean makeTransaction(double amount) {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Transaction amount must be greater than 0.");
            }

            if (amount > this.balance) {
                throw new IllegalStateException("Insufficient balance for the transaction.");
            }

            this.balance -= amount;
            return true;
        }catch (IllegalArgumentException | IllegalStateException e) {
            return false;
        }

    }



    In the Bank Class
    // Deposit Money
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }*/
}