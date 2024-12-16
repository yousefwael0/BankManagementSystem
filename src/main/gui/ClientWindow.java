package gui;

import javax.swing.*;
import services.Bank;
import models.user.Client;

import java.awt.*;

public class ClientWindow extends JFrame {
    private Bank bank;
    private Client client;

    public ClientWindow(Bank bank, Client client) {
        this.bank = bank;
        this.client = client;

        // Window setup
        setTitle("Client Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Client options
        JButton editInfoButton = new JButton("Edit Personal Information");
        JButton viewAccountButton = new JButton("View Account Details");
        JButton transactionHistoryButton = new JButton("View Transaction History");
        JButton depositButton = new JButton("Deposit Money");
        JButton transferButton = new JButton("Transfer Money");

        // Add listeners to buttons
        editInfoButton.addActionListener(e -> {
            // Implement personal information editing
        });
        viewAccountButton.addActionListener(e -> {
            // Show account details
        });
        transactionHistoryButton.addActionListener(e -> {
            // Show transaction history
        });
        depositButton.addActionListener(e -> {
            // Implement deposit logic
        });
        transferButton.addActionListener(e -> {
            // Implement transfer logic
        });

        // Add buttons to the window
        add(editInfoButton);
        add(viewAccountButton);
        add(transactionHistoryButton);
        add(depositButton);
        add(transferButton);

        setLocationRelativeTo(null); // Center the window
    }
}