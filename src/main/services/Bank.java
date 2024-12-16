package services;

import models.account.Transaction;
import models.user.Admin;
import models.user.Client;
import models.user.Employee;
import models.user.User;

import java.time.LocalDate;
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

    // Get transactions by employee
    public List<Transaction> getTransactionsByEmployee(Employee employee) {
        List<Transaction> transactionsByEmp = new ArrayList<>();
        for (Transaction transaction : this.getTransactions()){
            if (transaction.getEmployeeId().equals(employee.userId)){
                transactionsByEmp.add(transaction);
            }
        }
        return transactionsByEmp;
    }
}