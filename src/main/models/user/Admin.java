package models.user;

import java.util.List;
import models.account.Transaction;

public class Admin{
    // Attributes
    private String firstName;
    private String lastName;
    public final String username;
    public final String password;

    // Constructor
    public Admin(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.username = "admin";
        this.password = "admin";
    }

    // Getters / Setters
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.lastName = lastName;
    }

    // Methods
    void authorizeEmployee(List<Employee> employees, Employee employee) {
        // Logic for authorizing new employees
        for (Employee emp : employees) {
            if (employee.getUsername().equals(emp.getUsername()) && employee.getPassword().equals(emp.getPassword()) && employee.isActive()) {
                System.out.println("Employee Already Exists: " + employee.getFirstName() + " " + employee.getLastName());
                Employee.decreaseCounter();
            }
        }
        employee.setActive(true);
        employees.add(employee);
    }

    public void displayAllEmployees(List<Employee> employees) {
        // Logic to display all employees
        for (Employee emp : employees) {
            System.out.println(emp.getFirstName() + " " + emp.getLastName());
        }
    }

    public void displayAllClients(List<Client> clients) {
        // Logic to display all clients
        for (Client client : clients) {
            System.out.println(client.getFirstName() + " " + client.getLastName());
        }
    }

    public void showTransactions(List<Transaction> transactions) {
        // Logic to display transactions
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}