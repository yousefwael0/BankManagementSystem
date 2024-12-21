package services;

import models.account.Account;
import models.account.Transaction;
import models.user.Admin;
import models.user.Client;
import models.user.Employee;
import models.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank {

    private List<Client> clients;
    private List<Employee> employees;

    // Constructor
    public Bank(List<Client> clients, List<Employee> employees) {
        this.clients = clients != null ? clients : new ArrayList<>();
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    // Getters
    public List<Client> getClients() {return clients;}
    public Client getClient(String username, String password) {
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }
        throw new IllegalArgumentException("Username or password is incorrect");
    }
    public Client getClientById(String id){
        for (Client client : clients){
            if (id.equals(client.userId)){
                return client;
            }
        }
        throw new IllegalArgumentException("Client with id " + id + " not found.");
    }
    public Client getClientByUsername(String username){
        for (Client client : clients){
            if (username.equals(client.getUsername())){
                return client;
            }
        }
        throw new IllegalArgumentException("Client with username " + username + " not found.");
    }
    public Client getClientByName(String name){
        try{
            String firstName = name.trim().substring(0, name.indexOf(" "));
            String lastName = name.trim().substring(name.indexOf(" ") + 1);
            for (Client client : clients){
                if (firstName.equals(client.getFirstName()) && lastName.equals(client.getLastName())){
                    return client;
                }
            }
            throw new IllegalArgumentException("Client with name " + name + " not found.");
        } catch (StringIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Please enter first name and last name.");
        }
    }
    public Account getAccount(String accountNumber){
        for (Client client : clients){
            for (Account account : client.getAccounts()){
                if (accountNumber.equals(account.accountNumber)){
                    return account;
                }
            }
        }
        throw new IllegalArgumentException("Account number " + accountNumber + " not found.");
    }

    public List<Employee> getEmployees() {return employees;}
    public Employee getEmployeeById(String id){
        for (Employee employee : employees){
            if (id.equals(employee.userId)){
                return employee;
            }
        }
        throw new IllegalArgumentException("Employee with id " + id + " not found.");
    }
    public Employee getEmployeeByUsername(String username){
        for (Employee employee : employees){
            if (username.equals(employee.getUsername())){
                return employee;
            }
        }
        throw new IllegalArgumentException("Employee with username " + username + " not found.");
    }
    public Employee getEmployee(String username, String password){
        for (Employee employee : employees){
            if (username.equals(employee.getUsername()) && password.equals(employee.getPassword())){
                return employee;
            }
        }
        throw new IllegalArgumentException("Invalid username or password.");
    }
    public void authorizeEmployee(Employee employee){
        if (employee.isActive()){
            throw new IllegalArgumentException("Employee is already authorized.");
        }
        employee.setActive(true);
    }

    // Add Methods
    public void addClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        clients.add(client);
    }

    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        employees.add(employee);
    }
    // Authentication Method
    public User authenticateUser(String username, String password) {

        // Check if the user is an admin
        if (username.equals("admin") && password.equals("admin")) {
            return new Admin("Admin", "Administrator");
        }

        // Check if the user is an employee
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password) && employee.isActive()) {
                return employee;
            }
        }

        // Check if the user is a client
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }

        throw new IllegalArgumentException("Invalid username or password.");
    }
    // Get all transactions
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        for (Client client : clients) {
            transactions.addAll(client.getTransactionHistory());
        }
        return transactions;
    }
    // Get transactions by date
    public List<Transaction> getTransactionsByDate(LocalDate date) {
        List<Transaction> transactionsByDate = new ArrayList<>();
        for (Client client : clients) {
            transactionsByDate.addAll(client.getTransactionsByDate(date));
        }
        return transactionsByDate;
    }

    // Get transactions by client
    public List<Transaction> getTransactionsByClient(Client client) {
        return client.getTransactionHistory();
    }

    public void transferMoney(Client client, String senderAccountNumber, String receiverAccountNumber, double amount) {
        try{
            client.getAccount(senderAccountNumber).withdraw(amount);
            getAccount(receiverAccountNumber).deposit(amount);
            client.addTransaction(new Transaction(LocalDateTime.now(), "TRANSFER", amount, client.userId));
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Account> getAccounts(){
        List<Account> accounts = new ArrayList<>();
        for (Client client : clients) {
            accounts.addAll(client.getAccounts());
        }
        return accounts;
    }
}