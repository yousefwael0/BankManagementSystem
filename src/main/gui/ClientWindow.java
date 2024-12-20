package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import services.Bank;
import models.user.Client;
import models.account.Account;
import gui.LoginWindow;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientWindow extends JFrame {
    private final Bank bank;
    private final Client client;
    private final JPanel mainPanel;

    public ClientWindow(Bank bank, Client client) {
        this.bank = bank;
        this.client = client;

        // Window setup
        setTitle("Client Dashboard - Welcome " + client.getFirstName());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel with padding
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Create buttons with custom styling
        JButton[] buttons = {
                createStyledButton("Edit Personal Information", "Edit your profile details"),
                createStyledButton("View Account Details", "Check your account balance and information"),
                createStyledButton("View Transaction History", "See your recent transactions"),
                createStyledButton("Deposit Money", "Add funds to your account"),
                createStyledButton("Transfer Money", "Send money to another account"),
                createStyledButton("CreditCard", "create new CreditCard"),
                createStyledButton("Logout", "logging out")
        };

        // Add action listeners
        buttons[0].addActionListener(e -> showEditInfoDialog());
        buttons[1].addActionListener(e -> showAccountDetails());
        buttons[2].addActionListener(e -> showTransactionHistory());
        buttons[3].addActionListener(e -> showDepositDialog());
        buttons[4].addActionListener(e -> showTransferDialog());
        buttons[5].addActionListener(e -> showCreditCard());
        buttons[6].addActionListener(e -> showLogout());
        // Add buttons to panel
        for (JButton button : buttons) {
            mainPanel.add(button, gbc);
        }

        // Add main panel to frame
        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(300, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(240, 240, 240));
        button.setFocusPainted(false);
        return button;
    }

    private void showEditInfoDialog() {
        JDialog dialog = new JDialog(this, "Edit Personal Information", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        // Add form fields
        dialog.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField(client.getFirstName());
        dialog.add(firstNameField);

        dialog.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField(client.getLastName());
        dialog.add(lastNameField);

        dialog.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField(client.getPhoneNumber());
        dialog.add(phoneField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            Map<String, String> changes = new HashMap<>();
            changes.put("firstName", firstNameField.getText());
            changes.put("lastName", lastNameField.getText());
            changes.put("phoneNumber", phoneField.getText());

            client.editUserInfo(changes);
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Information updated successfully!");
        });

        dialog.add(saveButton);
        dialog.setVisible(true);
    }

    // ... (previous imports remain the same)

    private void showAccountDetails() {
        List<Account> accounts = client.getAccounts();
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found!", "Account Details",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Account account = accounts.get(0);
        String details = String.format("""
            Account Number: %s
            Balance: $%.2f
            Account Type: %s
            """,
                account.accountNumber,  // Direct field access
                account.getBalance(),   // Using getter
                account.accountType);   // Direct field access

        JOptionPane.showMessageDialog(this, details, "Account Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

// ... (rest of ClientWindow.java remains the same)

    private void showTransactionHistory() {
        JDialog dialog = new JDialog(this, "Transaction History", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);

        StringBuilder history = new StringBuilder();
        client.getTransactionHistory().forEach(transaction ->
                history.append(transaction.toString()).append("\n"));

        historyArea.setText(history.toString());

        JScrollPane scrollPane = new JScrollPane(historyArea);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showDepositDialog() {
        List<Account> accounts = client.getAccounts();
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No account found for deposit",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String amountStr = JOptionPane.showInputDialog(this,
                "Enter amount to deposit:",
                "Deposit Money",
                JOptionPane.PLAIN_MESSAGE);

        if (amountStr != null && !amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                Account account = accounts.get(0);
                account.deposit(amount);
                JOptionPane.showMessageDialog(this,
                        String.format("Successfully deposited $%.2f", amount));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid amount",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showTransferDialog() {
        List<Account> accounts = client.getAccounts();
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No account found for transfer",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Transfer Money", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        dialog.add(new JLabel("Amount:"));
        JTextField amountField = new JTextField();
        dialog.add(amountField);

        dialog.add(new JLabel("Account:"));
        JTextField accountField = new JTextField();
        dialog.add(accountField);

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Account sourceAccount = accounts.get(0);

                if (amount <= 0) {
                    throw new IllegalArgumentException("Amount must be positive");
                }
                if (amount > sourceAccount.getBalance()) {
                    throw new IllegalArgumentException("Insufficient funds");
                }

                sourceAccount.withdraw(amount);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, String.format("Successfully transferred " + amount + " to " + accounts  ));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a valid amount",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(transferButton);
        dialog.setVisible(true);
    }

    private void showLogout() {
        new LoginWindow(bank).setVisible(true);
        this.dispose();
    }


    private void showCreditCard()  {
        JDialog dialog = new JDialog(this, "CreditCard", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        dialog.setLocationRelativeTo(this);
         //comment

    }



}