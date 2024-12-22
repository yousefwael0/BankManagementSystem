package gui;

import javax.swing.*;
import java.awt.*;

import models.user.User;
import services.Bank;
import models.user.Admin;
import models.user.Client;
import models.user.Employee;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Bank bank;

    public LoginWindow() {
        initialize();
    }

    public void initialize() {
        bank = Bank.getInstance();

        // Window setup
        setTitle("Bank Management System - Login");
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window on screen

        // Add a title panel
        JLabel titleLabel = new JLabel("Welcome to the Bank System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Create input panel for username and password
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Add the input panel to the center
        add(inputPanel, BorderLayout.CENTER);

        // Add a button panel
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Montserrat", Font.BOLD, 14));
        loginButton.addActionListener(e ->authenticateUser());
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Trigger login when Enter is pressed in the password field
        passwordField.addActionListener(e -> loginButton.doClick());
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        User user;
        try {
            user = bank.authenticateUser(username, password);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return;
        }

        if (user instanceof Admin)
            new AdminWindow().setVisible(true);
        else if (user instanceof Employee)
            new EmployeeWindow((Employee) user).setVisible(true);
        else if (user instanceof Client)
            new ClientWindow((Client) user).setVisible(true);

        this.dispose(); // Close the login window
    }
}