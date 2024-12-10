package services;

import models.user.Client;
import models.user.Employee;
import java.util.List;

public class Bank {
    private List<Client> clients;
    private List<Employee> employees;

    public Bank(List<Client> clients, List<Employee> employees) {
        this.clients = clients;
        this.employees = employees;
    }

    public Client authenticateClient(String username, String password) {
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null; // Invalid login
    }

    public Employee authenticateEmployee(String username, String password) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null; // Invalid login
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Other methods for managing accounts, transactions, etc.
}