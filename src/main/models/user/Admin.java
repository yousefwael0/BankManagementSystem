package models.user;

import java.util.List;
import models.account.Transaction;

public class Admin extends User {

    public Admin(int ID, String firstName, String lastName, String username, String password) {
        super(ID, firstName, lastName, username, password);
    }

    public void authorizeEmployee(Employee employee) {
        // Logic for authorizing new employees
        System.out.println("Employee authorized: " + employee.getFirstName());
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

    @Override
    public void displayMenu() {
        System.out.println("Welcome, Admin.");
        System.out.println("1. Authorize Employee");
        System.out.println("2. Display Employees");
        System.out.println("3. Display Clients");
        System.out.println("4. View Transactions");
        System.out.println("5. Logout");
    }
}