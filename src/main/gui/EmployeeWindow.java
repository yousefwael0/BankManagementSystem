package gui;

import javax.swing.*;
import services.Bank;
import models.user.Employee;

import java.awt.*;

public class EmployeeWindow extends JFrame {
    private Bank bank;
    private Employee employee;

    public EmployeeWindow(Bank bank, Employee employee) {
        this.bank = bank;
        this.employee = employee;

        // Window setup
        setTitle("Employee Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Employee options
        JButton editInfoButton = new JButton("Edit Personal Information");
        JButton createClientButton = new JButton("Create Client Account");
        JButton searchClientButton = new JButton("Search for Client");
        JButton deleteClientButton = new JButton("Delete Client Account");

        // Add listeners to buttons
        editInfoButton.addActionListener(e -> {
            // Implement edit information logic
        });
        createClientButton.addActionListener(e -> {
            // Implement client creation logic
        });
        searchClientButton.addActionListener(e -> {
            // Implement client search logic
        });
        deleteClientButton.addActionListener(e -> {
            // Implement client deletion logic
        });

        // Add buttons to the window
        add(editInfoButton);
        add(createClientButton);
        add(searchClientButton);
        add(deleteClientButton);

        setLocationRelativeTo(null); // Center the window
    }
}