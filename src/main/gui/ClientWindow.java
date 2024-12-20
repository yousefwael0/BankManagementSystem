package gui;

import javax.swing.*;
import services.Bank;
import models.user.Client;
import models.account.Account;
import models.account.CreditCard;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
        setLayout(new GridLayout(7, 1));

        // Client options
        JButton editInfoButton = new JButton("Edit Personal Information");
        JButton viewAccountButton = new JButton("View Account Details");
        JButton transactionHistoryButton = new JButton("View Transaction History");
        JButton depositButton = new JButton("Deposit Money");
        JButton transferButton = new JButton("Transfer Money");
        JButton creditCardButton = new JButton("Ask for Credit Card");
        JButton paycreditCardButton = new JButton(" Pay With Credit Card");
        JButton discreditCardButton = new JButton(" Disable Credit Card");
        JButton logoutButton = new JButton("Logout");

        // Add listeners to buttons
        editInfoButton.addActionListener(e -> showEditInfoDialog());
        viewAccountButton.addActionListener(e -> showAccountDetails());
        transactionHistoryButton.addActionListener(e -> showTransactionHistory());
        depositButton.addActionListener(e -> showDepositDialog());
        transferButton.addActionListener(e -> showTransferDialog());
        creditCardButton.addActionListener(e -> showCreditCardDialog());
        paycreditCardButton.addActionListener(e -> showpayCreditCardDialog());
        discreditCardButton.addActionListener(e -> showdisCreditCardDialog());
        logoutButton.addActionListener(e -> logout());

        // Add buttons to the window
        add(editInfoButton);
        add(viewAccountButton);
        add(transactionHistoryButton);
        add(depositButton);
        add(transferButton);
        add(creditCardButton);
        add(paycreditCardButton);
        add(discreditCardButton);
        add(logoutButton);

        setLocationRelativeTo(null); // Center the window
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
                account.accountNumber,
                account.getBalance(),
                account.accountType);

        JOptionPane.showMessageDialog(this, details, "Account Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

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

        dialog.add(new JLabel("Recipient Account:"));
        JTextField recipientField = new JTextField();
        dialog.add(recipientField);

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String recipientAccount = recipientField.getText();

                Account sourceAccount = accounts.get(0);
                // bank.transfer(sourceAccount, recipientAccount, amount);

                dialog.dispose();
                JOptionPane.showMessageDialog(this, String.format("Successfully transferred $%.2f to %s", amount, recipientAccount));
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

    private void showCreditCardDialog() {
        JDialog dialog = new JDialog(this, "Credit Card", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 1, 10, 10));
        dialog.setLocationRelativeTo(this);

        //CreditCard creditCard = client.getCreditCard();

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton);

        dialog.setVisible(true);
    }


    private void showpayCreditCardDialog() {
        JDialog dialog = new JDialog(this, "Credit Card", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 1, 10, 10));
        dialog.setLocationRelativeTo(this);


        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton);

        dialog.setVisible(true);
    }

    private void showdisCreditCardDialog() {
        JDialog dialog = new JDialog(this, "Credit Card", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 1, 10, 10));
        dialog.setLocationRelativeTo(this);



        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton);

        dialog.setVisible(true);
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out.");
        this.dispose();
        new LoginWindow(bank).setVisible(true);
    }
}



