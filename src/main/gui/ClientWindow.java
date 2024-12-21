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

                // If all validations pass, proceed with saving
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
        JDialog dialog = new JDialog(this, "Transaction History", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout());

        // Create text area for displaying transactions
        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);

        // Add "All Transactions" button
        JButton allTransactionsBtn = new JButton("All Transactions");
        allTransactionsBtn.addActionListener(e -> {
            StringBuilder history = new StringBuilder();
            getAllTransactions().forEach(transaction ->
                    history.append(transaction.toString()).append("\n"));
            historyArea.setText(history.toString());
        });

        // Add date filter components
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(10);
        JButton dateFilterBtn = new JButton("Filter by Date");
        dateFilterBtn.addActionListener(e -> {
            try {
                java.time.LocalDate date = java.time.LocalDate.parse(dateField.getText());
                StringBuilder history = new StringBuilder();
                getTransactionsByDate(date).forEach(transaction ->
                        history.append(transaction.toString()).append("\n"));
                historyArea.setText(history.toString());
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter date in format yyyy-MM-dd",
                        "Invalid Date Format",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to filter panel
        filterPanel.add(allTransactionsBtn);
        filterPanel.add(dateLabel);
        filterPanel.add(dateField);
        filterPanel.add(dateFilterBtn);

        // Add components to main panel
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add main panel to dialog
        dialog.add(mainPanel);

        // Show all transactions initially
        StringBuilder history = new StringBuilder();
        getAllTransactions().forEach(transaction ->
                history.append(transaction.toString()).append("\n"));
        historyArea.setText(history.toString());

        dialog.setVisible(true);
    }

    private List<models.account.Transaction> getAllTransactions() {
        return client.getTransactionHistory();
    }

    private List<models.account.Transaction> getTransactionsByDate(java.time.LocalDate date) {
        return client.getTransactionHistory().stream()
                .filter(transaction -> transaction.getDate().toLocalDate().equals(date))
                .collect(java.util.stream.Collectors.toList());
    }

     private void showDepositDialog () {
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

     private void showCreditCardDialog () {
         JDialog dialog = new JDialog(this, "Credit Card", true);
         dialog.setSize(300, 200);
         dialog.setLayout(new GridLayout(2, 1, 10, 10));
         dialog.setLocationRelativeTo(this);


        // Account AccountNumber =AccountNumberField.g
         // client.getAccount(AccountNumber);

         JButton closeButton = new JButton("Close");
         closeButton.addActionListener(e -> dialog.dispose());
         dialog.add(closeButton);

         dialog.setVisible(true);
     }


     private void showpayCreditCardDialog () {
         JDialog dialog = new JDialog(this, "Credit Card", true);
         dialog.setSize(300, 200);
         dialog.setLayout(new GridLayout(2, 1, 10, 10));
         dialog.setLocationRelativeTo(this);


         JButton closeButton = new JButton("Close");
         closeButton.addActionListener(e -> dialog.dispose());
         dialog.add(closeButton);

         dialog.setVisible(true);
     }

     private void showdisCreditCardDialog () {
         JDialog dialog = new JDialog(this, "Credit Card", true);
         dialog.setSize(300, 200);
         dialog.setLayout(new GridLayout(2, 1, 10, 10));
         dialog.setLocationRelativeTo(this);






         JButton closeButton = new JButton("Close");
         closeButton.addActionListener(e -> dialog.dispose());
         dialog.add(closeButton);

         dialog.setVisible(true);
     }

     private void logout () {

         this.dispose();
         new LoginWindow(bank).setVisible(true);

     }


}




