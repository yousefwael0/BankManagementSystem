import gui.LoginWindow;
import models.account.Account;
import models.account.Transaction;
import models.user.User;
import services.Bank;
import services.FileManager;

import com.google.gson.reflect.TypeToken;
import models.user.Client;
import models.user.Employee;

import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Step 1: Initialize the Bank system with data
        Bank bank = initializeBank();

        // Setting the initial id counters to avoid conflicts with save files
        User.setCounter(bank.getClients().size()+bank.getEmployees().size()+1);
        Transaction.setCounter(bank.getTransactions().size()+1);
        Account.setCounter(bank.getAccounts().size()+1);
        System.out.println("transaction count: " + Transaction.getCounter());
        System.out.println("user counter: " + User.getCounter());

        // Step 2: Launch the Login Window
        new LoginWindow(bank).setVisible(true);
    }

    // Method to initialize the Bank system
    private static Bank initializeBank() {
        System.out.println("Initializing Bank Management System...");

        // File paths for JSON data
        String clientsFilePath = "src/main/data/clients.json";
        String employeesFilePath = "src/main/data/employees.json";

        // Load clients and employees
        List<Client> clients = loadClientsFromFile(clientsFilePath);
        List<Employee> employees = loadEmployeesFromFile(employeesFilePath);

        // Create and return the Bank instance
        return new Bank(clients, employees);
    }

    // Method to load clients from JSON
    private static List<Client> loadClientsFromFile(String filePath) {
        try {
            Type clientListType = new TypeToken<List<Client>>() {}.getType();
            return FileManager.loadFromJson(filePath, clientListType);
        } catch (Exception e) {
            System.err.println("Error loading clients from file: " + e.getMessage());
            return List.of(); // Return an empty list if loading fails
        }
    }

    // Method to load employees from JSON
    private static List<Employee> loadEmployeesFromFile(String filePath) {
        try {
            Type employeeListType = new TypeToken<List<Employee>>() {}.getType();
            return FileManager.loadFromJson(filePath, employeeListType);
        } catch (Exception e) {
            System.err.println("Error loading employees from file: " + e.getMessage());
            return List.of(); // Return an empty list if loading fails
        }
    }
}