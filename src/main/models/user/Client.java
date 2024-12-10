package models.user;

import java.util.ArrayList;
import java.util.List;
import models.account.Account;
import models.loyalty.LoyaltyPoints;

public class Client extends User {
    private String telephone;
    private String state; // Active or Closed
    private List<Account> accounts;
    private LoyaltyPoints loyaltyPoints;

    public Client(int ID, String firstName, String lastName, String username, String password, String telephone, String state) {
        super(ID, firstName, lastName, username, password);
        this.telephone = telephone;
        this.state = state;
        this.accounts = new ArrayList<>();
        this.loyaltyPoints = new LoyaltyPoints(ID);  // Initialize loyalty points
    }

    public LoyaltyPoints getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    @Override
    public void displayMenu() {
        System.out.println("Welcome, " + getFirstName());
        System.out.println("1. View Account Details");
        System.out.println("2. Transfer Money");
        System.out.println("3. View Transaction History");
        System.out.println("4. Request Credit Card");
        System.out.println("5. View Loyalty Points");
        System.out.println("6. Logout");
    }

    public void earnLoyaltyPoints(int points) {
        this.loyaltyPoints.addPoints(points);
    }
}