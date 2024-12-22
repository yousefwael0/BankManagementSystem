package gui;

import javax.swing.*;

import models.account.CreditCard;
import models.user.User;
import services.Bank;
import models.user.Client;
import models.account.Account;
import services.FileManager;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ClientWindow extends JFrame {
    private Bank bank;
    private Client client;


    public ClientWindow(Bank bank, Client client) {
        this.bank = bank;
        this.client = client;

        // Add a WindowListener to handle the close button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                System.exit(0); // Exit the program
            }
        });

        // Window setup
        setTitle("Client Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create header
        JLabel headerLabel = new JLabel("Welcome, " + client.getFirstName(), JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


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
        buttonPanel.add(editInfoButton);
        buttonPanel.add(viewAccountButton);
        buttonPanel.add(transactionHistoryButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(creditCardButton);
        buttonPanel.add(paycreditCardButton);
        buttonPanel.add(discreditCardButton);

        add(buttonPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null); // Center the window

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));


        logoutButton.addActionListener(e -> logout());
        footerPanel.add(logoutButton);

        add(footerPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);

    }

    private void saveData() {
        try {
            String clientsFilePath = "src/main/data/clients.json";
            String employeesFilePath = "src/main/data/employees.json";

            FileManager.saveToJson(clientsFilePath, bank.getClients());
            FileManager.saveToJson(employeesFilePath, bank.getEmployees());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showEditInfoDialog() {
        JDialog dialog = new JDialog(this, "Edit Personal Information", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridLayout(6, 2, 5, 5));// 2 col wa7d le label wa wa7d le text box wa 6 row 3l4ane wa7d le save button
        dialog.setLocationRelativeTo(this);

        // Add form fields
        dialog.add(new JLabel("First Name:"));
        JTextField FirstNameField = new JTextField(client.getFirstName());
        dialog.add(FirstNameField);

        dialog.add(new JLabel("Last Name:"));
        JTextField LastNameField = new JTextField(client.getLastName());
        dialog.add(LastNameField);

        dialog.add(new JLabel("Username:"));
        JTextField UsernameField = new JTextField(client.getUsername());
        dialog.add(UsernameField);

        dialog.add(new JLabel("Password:"));
        JTextField PasswordField = new JTextField(client.getPassword());
        dialog.add(PasswordField);

        dialog.add(new JLabel("Phone:"));
        JTextField PhoneField = new JTextField(client.getPhoneNumber());
        dialog.add(PhoneField);

        JButton saveButton = new JButton("Save Changes");
        dialog.add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                // Check for empty fields
                if (FirstNameField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("First Name cannot be empty");
                }
                if (LastNameField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Last Name cannot be empty");
                }
                if (UsernameField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Username cannot be empty");
                }
                if (PasswordField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Password cannot be empty");
                }
                if (PhoneField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Phone number cannot be empty");
                }


                Map<String, String> changes = new HashMap<>();
                changes.put("firstName", FirstNameField.getText().trim());
                changes.put("lastName", LastNameField.getText().trim());
                changes.put("userName", UsernameField.getText().trim());
                changes.put("passwordName", PasswordField.getText().trim());
                changes.put("phoneNumber", PhoneField.getText().trim());

                client.editUserInfo(changes);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Information updated successfully!");

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "An error occurred while saving: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }



     private void showAccountDetails () {
         List<Account> accounts = client.getAccounts();
         if (accounts.isEmpty()) {
             JOptionPane.showMessageDialog(this, "No accounts found!", "Account Details", JOptionPane.WARNING_MESSAGE);
             return;
         }

         String[] accountOptions = accounts.stream()
                 .map(account -> "Account Number: " + account.accountNumber)
                 .toArray(String[]::new);

         String selectedAccount = (String) JOptionPane.showInputDialog(
                 this,
                 "Select an account to view details:",
                 "Account Details",
                 JOptionPane.QUESTION_MESSAGE,
                 null,
                 accountOptions,
                 accountOptions[0]);

         if (selectedAccount != null) {
             int selectedIndex = java.util.Arrays.asList(accountOptions).indexOf(selectedAccount);
             Account account = accounts.get(selectedIndex);

             String details = String.format("""
                Account Number: %s
                Balance: $%.2f
                Account Type: %s
                """,
                     account.accountNumber,
                     account.getBalance(),
                     account.accountType);

             JOptionPane.showMessageDialog(this, details, "Account Details", JOptionPane.INFORMATION_MESSAGE);
         }

     }
    private void showTransactionHistory() {
        List<Account> accounts = client.getAccounts();
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No accounts found!",
                    "Transaction History",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] accountOptions = accounts.stream()
                .map(account -> "Account Number: " + account.accountNumber)
                .toArray(String[]::new);

        String selectedAccount = (String) JOptionPane.showInputDialog(
                this,
                "Select an account to view transactions:",
                "Transaction History",
                JOptionPane.QUESTION_MESSAGE,
                null,
                accountOptions,
                accountOptions[0]);

        if (selectedAccount != null) {
            int selectedIndex = java.util.Arrays.asList(accountOptions).indexOf(selectedAccount);
            Account account = accounts.get(selectedIndex);

            JDialog dialog = new JDialog(this, "Transaction History", true);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(this);

            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Filter panel
            JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            JButton allTransactionsBtn = new JButton("All Transactions");
            JLabel dateLabel = new JLabel("Filter by Date (YYYY-MM-DD):");
            JTextField dateField = new JTextField(10);
            JButton dateFilterBtn = new JButton("Search");

            filterPanel.add(allTransactionsBtn);
            filterPanel.add(dateLabel);
            filterPanel.add(dateField);
            filterPanel.add(dateFilterBtn);

            // Transaction display area
            JTextArea historyArea = new JTextArea();
            historyArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(historyArea);

            mainPanel.add(filterPanel, BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Add header with account info
            historyArea.setText(String.format("Transaction History for Account: %s\n\n",
                    account.accountNumber));

            // Button actions
            allTransactionsBtn.addActionListener(e -> {
                List<models.account.Transaction> transactions = getAllTransactions();
                displayTransactions(transactions, historyArea, account.accountNumber);
            });

            dateFilterBtn.addActionListener(e -> {
                String dateStr = dateField.getText().trim();
                if (dateStr.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter a date",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    java.time.LocalDate date = java.time.LocalDate.parse(dateStr);
                    List<models.account.Transaction> transactions = getTransactionsByDate(date);
                    displayTransactions(transactions, historyArea, account.accountNumber);
                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter date in format YYYY-MM-DD",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Show initial transactions
            List<models.account.Transaction> transactions = getAllTransactions();
            displayTransactions(transactions, historyArea, account.accountNumber);

            dialog.add(mainPanel);
            dialog.setVisible(true);
        }
    }

    private void displayTransactions(List<models.account.Transaction> transactions, JTextArea historyArea, String accountNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Transaction History for Account: %s\n\n", accountNumber));

        if (transactions.isEmpty()) {
            sb.append("No transactions found.");
        } else {
            transactions.forEach(transaction ->
                    sb.append(transaction.toString()).append("\n"));
        }

        historyArea.setText(sb.toString());
    }

    private List<models.account.Transaction> getAllTransactions() {
        return client.getTransactionHistory();
    }

    private List<models.account.Transaction> getTransactionsByDate(java.time.LocalDate date) {
        return client.getTransactionHistory().stream()
                .filter(transaction -> transaction.getDate().toLocalDate().equals(date))
                .collect(java.util.stream.Collectors.toList());
    }

    private void showDepositDialog() {
        List<Account> accounts = client.getAccounts();
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No accounts found!",
                    "Deposit",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] accountOptions = accounts.stream()
                .map(account -> "Account Number: " + account.accountNumber)
                .toArray(String[]::new);

        String selectedAccount = (String) JOptionPane.showInputDialog(
                this,
                "Select an account for deposit:",
                "Deposit",
                JOptionPane.QUESTION_MESSAGE,
                null,
                accountOptions,
                accountOptions[0]);

        if (selectedAccount != null) {
            int selectedIndex = java.util.Arrays.asList(accountOptions).indexOf(selectedAccount);
            Account account = accounts.get(selectedIndex);

            String amountStr = JOptionPane.showInputDialog(this,
                    "Enter amount to deposit:",
                    "Deposit Money",
                    JOptionPane.PLAIN_MESSAGE);

            if (amountStr != null && !amountStr.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    account.deposit(amount);

                    String successMessage = String.format("""
                    Deposit Successful!
                    Account Number: %s
                    Amount Deposited: $%.2f
                    New Balance: $%.2f
                    """,
                            account.accountNumber,
                            amount,
                            account.getBalance());

                    JOptionPane.showMessageDialog(this,
                            successMessage,
                            "Deposit Successful",
                            JOptionPane.INFORMATION_MESSAGE);

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
    }
     private void showTransferDialog () {
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

         dialog.add(new JLabel("Receiver Account:"));
         JTextField receiverField = new JTextField();
         dialog.add(receiverField);


         dialog.add(new JLabel("sender Account:"));
         JTextField senderField = new JTextField();
         dialog.add(senderField);


         JButton transferButton = new JButton("Transfer");
         transferButton.addActionListener(e -> {
             try {
                 double amount = Double.parseDouble(amountField.getText());
                 String receiverAccount = receiverField.getText();
                 // Account senderAccount = accounts.get(0);
                 String senderAccount = senderField.getText();
                 bank.transferMoney(client, senderAccount, receiverAccount, amount);

                 dialog.dispose();
                 JOptionPane.showMessageDialog(this, String.format("Successfully transferred $%.2f to %s", amount, receiverAccount));
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

//3shan yask 3al credit card

    private void showCreditCardDialog () {
        JDialog dialog = new JDialog(this, "Credit Card", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridLayout(4, 1, 10, 10));
        dialog.setLocationRelativeTo(this);


        JLabel accountLabel = new JLabel("Enter Account Number:");
        JTextField accountNumberField = new JTextField();
        JButton requestButton = new JButton("Request Credit Card");
        JButton closeButton = new JButton("Close");


        dialog.add(accountLabel);
        dialog.add(accountNumberField);
        dialog.add(requestButton);
        dialog.add(closeButton);


        requestButton.addActionListener(e -> {
            String accountNumber = accountNumberField.getText().trim();

            try {
                // byakhod el account
                Account account = client.getAccount(accountNumber);

                // byask for el credit card
                account.askForCreditCard();

                // messege en heya eshtghlet
                JOptionPane.showMessageDialog(dialog,
                        "Credit Card requested successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {

                //messege en heya bayza
                JOptionPane.showMessageDialog(dialog,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        // Action listener for the close button
        closeButton.addActionListener(e -> dialog.dispose());

        // Show the dialog
        dialog.setVisible(true);
    }

    // 3ashan adf3 bel credit card

    private void showpayCreditCardDialog() {
        JDialog dialog = new JDialog(this, "Credit Card Payment", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(4, 1, 10, 10));
        dialog.setLocationRelativeTo(this);

        // add lel account number w payment amount
        JLabel accountLabel = new JLabel("Enter Account Number:");
        JTextField accountField = new JTextField();

        JLabel amountLabel = new JLabel("Enter Payment Amount:");
        JTextField amountField = new JTextField();

        //el pay button
        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> {
            try {
                //byshoof el account
                String accountNumber = accountField.getText().trim();
                if (accountNumber.isEmpty()) {
                    throw new IllegalArgumentException("Please enter an account number");
                }

                Account account = client.getAccount(accountNumber);

                // byshoof el amount
                double amount = Double.parseDouble(amountField.getText());
                client.earnLoyaltyPoints((int) Math.round(amount * 0.25));


                // by check 3al limit
                if (amount > 20000) {
                    throw new IllegalArgumentException(
                            String.format("Amount exceeds credit limit. Maximum: %.2f LE, Requested: %.2f LE",
                                    20000.0, amount)
                    );
                }

                // byt2ked el credit card mwgood wla la
                if (account.getCreditCard() == null) {
                    throw new IllegalArgumentException("No credit card found for this account");
                }

                // bydf3 bel limit bs
                account.getCreditCard().makePayment(amount);

                JOptionPane.showMessageDialog(dialog,
                        String.format("Payment of %.2f LE processed successfully!", amount),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a valid number for payment amount",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        // dialog le koll haga
        dialog.add(accountLabel);
        dialog.add(accountField);
        dialog.add(amountLabel);
        dialog.add(amountField);
        dialog.add(payButton);
        dialog.add(closeButton);

        dialog.setVisible(true);
    }

// disable  credit card

    private void showdisCreditCardDialog() {
        List<Account> accounts = client.getAccounts();
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No accounts found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3shan a select account
        String[] accountOptions = accounts.stream()
                .map(account -> account.accountNumber)
                .toArray(String[]::new);

        String selectedAccount = (String) JOptionPane.showInputDialog(
                this,
                "Select account to disable credit card:",
                "Disable Credit Card",
                JOptionPane.QUESTION_MESSAGE,
                null,
                accountOptions,
                accountOptions[0]);

        if (selectedAccount != null) {
            Account account = accounts.stream()
                    .filter(acc -> acc.accountNumber.equals(selectedAccount))
                    .findFirst()
                    .orElse(null);

            if (account != null) {
                CreditCard card = account.getCreditCard();
                if (card == null) {
                    JOptionPane.showMessageDialog(this,
                            "No credit card found for this account",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // 3shan a disbale el credit card
                    card.disableCard(card.cardNumber);
                    JOptionPane.showMessageDialog(this,
                            "Credit card has been successfully disabled",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this,
                            e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

     private void logout () {
         saveData();
         new LoginWindow(bank).setVisible(true);
         this.dispose();
     }
}




