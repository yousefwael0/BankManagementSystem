import gui.LoginWindow;
import models.account.Account;
import models.account.Transaction;
import models.user.User;
import services.Bank;

public class Main {
    public static void main(String[] args) {
        // Step 1: Initialize the Bank system with data
        Bank bank = Bank.getInstance();

        // Setting the initial id counters to avoid conflicts with save files
        User.setCounter(bank.getClients().size()+bank.getEmployees().size()+1);
        Transaction.setCounter(bank.getTransactions().size()+1);
        Account.setCounter(bank.getAccounts().size()+1);
        System.out.println("transaction count: " + Transaction.getCounter());
        System.out.println("user counter: " + User.getCounter());

        // Step 2: Launch the Login Window
        new LoginWindow().setVisible(true);
    }
}