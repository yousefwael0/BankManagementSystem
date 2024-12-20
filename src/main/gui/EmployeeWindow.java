package gui;
import javax.swing.*;

import models.account.Account;
import models.account.CurrentAccount;
import models.account.SavingsAccount;
import services.Bank;
import models.user.Employee;
import models.user.Client;
import services.FileManager;
import models.user.Client;
import gui.LoginWindow;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class EmployeeWindow extends JFrame {
    private Bank bank;
    private Employee employee;
    private JTextArea functionOutputArea;

    private JTextField makeTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }

    private JTextField makeNumericField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 30));

        // Add a DocumentFilter to allow fractional numbers
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength() - length) + string)) {
                    super.replace(fb, offset, length, string, attr);
                }
            }

            private boolean isValidInput(String text) {
                return text.matches("-?\\d*(\\.\\d*)?"); // Matches optional '-', digits, and optional decimal
            }
        });

        return textField;
    }

    private JPasswordField makePasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        return passwordField;
    }

    private JComboBox<String> comboBox(String first, String second) {
        String[] options = {first, second};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(-1);
        return comboBox;
    }

    private void createClient(){
               JTextField firstNameField = makeTextField();
               JTextField lastNameField = makeTextField();
               JTextField usernameField = makeTextField();
               JPasswordField passwordField = makePasswordField();
               JTextField phonenumberField = makeTextField();

               Object[] fields3 = {
                       "Enter Client's First Name: ", firstNameField,
                       "Enter Client's Last Name: ", lastNameField,
                       "Enter Client's Phone Number: ", phonenumberField,
                       "Client's Username: ", usernameField,
                       "Client's Password: ", passwordField
               };

               int response = JOptionPane.OK_OPTION;

               while (response != JOptionPane.CANCEL_OPTION && response != JOptionPane.CLOSED_OPTION){
                   response = JOptionPane.showConfirmDialog(this,
                           fields3,
                           "Create New Client",
                           JOptionPane.OK_CANCEL_OPTION,
                           JOptionPane.PLAIN_MESSAGE);

                   if (response == JOptionPane.OK_OPTION){
                       String firstName = firstNameField.getText();
                       String lastName = lastNameField.getText();
                       String PhoneNumber = phonenumberField.getText();
                       String userName = usernameField.getText();
                       String password = new String(passwordField.getPassword());

                       if (userName.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || PhoneNumber.isEmpty()){
                           JOptionPane.showMessageDialog(
                                   this,
                                   "All fields must be filled in. Please provide valid information for all fields.",
                                   "Error",
                                   JOptionPane.ERROR_MESSAGE
                           );
                       }
                       else{
                           try{
                               //logic el create new client
                              Client client = new Client(firstName, lastName, userName, password, PhoneNumber);
                              bank.addClient(client);
                               JOptionPane.showMessageDialog(
                                       this,
                                       "New client created successfully.",
                                       "Success",
                                       JOptionPane.INFORMATION_MESSAGE);
                               return;
                           }catch(IllegalArgumentException exp){
                               JOptionPane.showMessageDialog(
                                       this,
                                       exp.getMessage(),
                                       "Error",
                                       JOptionPane.ERROR_MESSAGE
                               );
                               continue;
                           }
                       }
                   }
               }
    }

    private void createClientAcc() {
        JTextField usernameField = makeTextField();
        JPasswordField passwordField = makePasswordField();
        JComboBox <String> accountTypeField = comboBox("Savings", "Current");
        JTextField balanceField = makeNumericField();
        JTextField interestRfield = makeNumericField();

        Object[] fields = {
                "Enter client's username:", usernameField,
                "Enter client's password:", passwordField,
                "Enter client's account type:", accountTypeField,
                "Enter client's balance: ", balanceField,
                "Enter client's interest rate: ", interestRfield
        };

        int response = JOptionPane.OK_OPTION;

        while (response != JOptionPane.CANCEL_OPTION  && response != JOptionPane.CLOSED_OPTION) {
            response = JOptionPane.showConfirmDialog(
                    this,
                    fields,
                    "Create New Client Account",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (response == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String accountType = (String) accountTypeField .getSelectedItem();
                String balance = balanceField.getText();
                String intrestRate = interestRfield.getText();

                if (username.isEmpty() || password.isEmpty() || accountType == null || balance.isEmpty() || intrestRate.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "All fields must be filled in. Please provide valid information for all fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    try {
                        // Done
                        Client client = bank.getClientByUsername(username);
                        if (accountType.equals("Savings")) {
                            client.addAccount(new SavingsAccount(Double.parseDouble(balance), Double.parseDouble(intrestRate), client.userId));
                        }
                        else if (accountType.equals("Current")) {
                            client.addAccount(new CurrentAccount(Double.parseDouble(balance), client.userId));
                        }
                        JOptionPane.showMessageDialog(
                                this,
                                "New client Account created successfully.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        continue;
                    }
                }
            }
        }
    }
    private void deleteClientAcc(){
        // This function should take more input to be able to identify the account to be deleted for which client
        // it already has username and password
        // a field for account number should be added for searching in the accounts list for the given client

        JTextField usernameField = makeTextField();
        JPasswordField passwordField = makePasswordField();
        JTextField accountNumField = makeTextField();


        Object[] fields2 = {
                "Enter client's Account Number:", accountNumField,
                "Enter client's username:", usernameField,
                "Enter client's password:", passwordField

        };
        int response2 = JOptionPane.OK_OPTION;

        while (response2 != JOptionPane.CANCEL_OPTION  && response2 != JOptionPane.CLOSED_OPTION) {
            response2 = JOptionPane.showConfirmDialog(
                    this,
                    fields2,
                    "Delete Client Account",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (response2 == JOptionPane.OK_OPTION){
                String username = usernameField.getText();
                String password = passwordField.getText();
                String accountNumber = accountNumField.getText();

                if (username.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(this,
                            "please provide information for all fields",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    boolean accountDeleted = deleteAccount(username, password, accountNumber); // Add the account number to the function
                    if (accountDeleted) {
                        JOptionPane.showMessageDialog(this,
                                "Account deleted successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Account deletion failed. Invalid credentials.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void editClientInfo() {
        JTextField usernameField = makeTextField();
        JTextField balanceField = makeNumericField();
        JTextField interestRateField = makeNumericField();
        JComboBox<String> statusComboBox = comboBox("Active", "Inactive");

        Object[] searchFields = {
                "Enter client's username to edit:", usernameField
        };
        Object[] editFields = {
                "Edit Client Information:",
                "Enter new balance:", balanceField,
                "Enter new interest rate:", interestRateField,
                "Select account status:", statusComboBox
        };
        //  Search for the client
        int searchResponse = JOptionPane.showConfirmDialog(
                this,
                searchFields,
                "Search Client to Edit",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (searchResponse == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a username to search for the client.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            Client client;
            try {
                client = bank.getClientByUsername(username);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Prompt for editing client information
            int editResponse = JOptionPane.OK_OPTION;

            while (editResponse != JOptionPane.CANCEL_OPTION && editResponse != JOptionPane.CLOSED_OPTION) {
                editResponse = JOptionPane.showConfirmDialog(
                        this,
                        editFields,
                        "Edit Client Information",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (editResponse == JOptionPane.OK_OPTION) {
                    String balance = balanceField.getText();
                    String interestRate = interestRateField.getText();
                    String status = (String) statusComboBox.getSelectedItem();

                    // Validate inputs
                    if (balance.isEmpty() || interestRate.isEmpty() || status == null) {
                        JOptionPane.showMessageDialog(
                                this,
                                "All fields must be filled. Please provide valid information.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    } else {

                        try {

                            // Compare current values with new input values
                          /*  boolean isSameBalance = balance.equals(String.valueOf(client.getBalance()));
                            boolean isSameInterestRate = interestRate.equals(String.valueOf(client.getInterestRate()));
                            boolean isSameStatus = status.equals(client.getStatus());

                            if (isSameBalance && isSameInterestRate && isSameStatus) {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "No changes were made. The information entered is the same as the current data.",
                                        "No Changes",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                return; // Exit without making any changes
                            }*/

                            // Creating a map to hold the changes
                            Map<String, String> changes = new HashMap<>();
                            // Add changes to the map
                            changes.put("balance", balance);
                            changes.put("interestRate", interestRate);
                            changes.put("status", status);
                            // Assuming the client object has a method to update information based on the changes map
                            // client.updateClientInfo(changes);

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Client information updated successfully.\n" +
                                            "Username: " + client.getUsername() + "\n" +
                                            "New Balance: " + balance + "\n" +
                                            "New Interest Rate: " + interestRate + "\n" +
                                            "New Status: " + status,
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            return;

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Please enter valid numeric values for balance and interest rate.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "An error occurred while updating the client information: " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
                // editing client info takes different attributes from his account
                // it takes (Map<String, String> changes) a map of strings to change
                // firstName, lastName, username, password, phoneNumber
                // the gui can be implemented similar to the edit personal info function




            }
        }
    }
    private void editPersonalInfo() {
        // Flawless.
        // Save the old values of the address and position for the confirmation message
        String oldAddress = employee.getAddress();
        String oldPosition = employee.getPosition();

        JTextField addressField = makeTextField();
        JTextField positionField = makeTextField();

        // Set initial values from the employee
        addressField.setText(oldAddress);
        positionField.setText(oldPosition);

        Object[] fields = {
                "Enter new address:", addressField,
                "Enter new position:", positionField
        };
        int response = JOptionPane.OK_OPTION;

        while (response != JOptionPane.CANCEL_OPTION && response != JOptionPane.CLOSED_OPTION) {
            response = JOptionPane.showConfirmDialog(
                    this,
                    fields,
                    "Edit Personal Information",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (response == JOptionPane.OK_OPTION) {
                // Get values from the fields
                String newAddress = addressField.getText();
                String newPosition = positionField.getText();

                // Validation: Check if both fields are filled in
                if (newAddress.isEmpty() || newPosition.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Both address and position must be provided.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        // Set new address and position for the employee
                        employee.setAddress(newAddress);
                        employee.setPosition(newPosition);
                        StringBuilder confirmationMessage = new StringBuilder("Personal information updated successfully.\n\n");

                        if (!oldAddress.equals(newAddress)) {
                            confirmationMessage.append("Address changed from: ").append(oldAddress)
                                    .append(" to: ").append(newAddress).append("\n");
                        }
                        if (!oldPosition.equals(newPosition)) {
                            confirmationMessage.append("Position changed from: ").append(oldPosition)
                                    .append(" to: ").append(newPosition).append("\n");
                        }
                        // Display the confirmation message in the functionOutputArea
                        functionOutputArea.setText("");
                        functionOutputArea.append(confirmationMessage.toString());
                        return;
                    }
                    catch (Exception ex) {
                        // Handle any exception
                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        continue;
                    }
                }
            }
        }
    }

    private void searchClient() {
        // Create radio buttons for selecting the search type
        JRadioButton searchByNameButton = new JRadioButton("Search by Name");
        JRadioButton searchByNumberButton = new JRadioButton("Search by Account Number");
        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(searchByNameButton);
        searchGroup.add(searchByNumberButton);

        Object[] initialFields = {
                "Select search type:", searchByNameButton, searchByNumberButton
        };

        // First dialog to choose the search type
        int initialResponse = JOptionPane.showConfirmDialog(
                this,
                initialFields,
                "Select Search Type",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (initialResponse == JOptionPane.OK_OPTION) {
            if (!searchByNameButton.isSelected() && !searchByNumberButton.isSelected()) {
                JOptionPane.showMessageDialog(this,
                        "Please select a search type (Name or Account Number).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return; // Exit if no selection is made
            }

            // Create a text field for input based on the selected search type
            JTextField inputField = makeTextField();
            String searchPrompt = searchByNameButton.isSelected() ? "Enter client name:" : "Enter account number:";
            Object[] inputFields = {
                    searchPrompt, inputField
            };

            // Second dialog to enter the search value
            int inputResponse = JOptionPane.showConfirmDialog(
                    this,
                    inputFields,
                    "Enter Search Value",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (inputResponse == JOptionPane.OK_OPTION) {
                String input = inputField.getText();

                // Validate input
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please provide a value for the search.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Exit if no value is provided
                }

                // Handle searching by name
                Client client = null;
                if (searchByNameButton.isSelected()) {
                    functionOutputArea.append("Searching by name...\n");
                    // Client client = bank.getClientByName(input); // Replace with actual method to search by name
                    // Done but add the gui to display the client's info
                    try{
                        client = bank.getClientByName(input);
                        displayClientInfo(client);
                    } catch(IllegalArgumentException ex){
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Handle searching by account number
                else if (searchByNumberButton.isSelected()) {

                    functionOutputArea.append("Searching by account Number...\n");
                    // client = bank.getClientByNumber(input);
                    // Done but add gui to display the client's info

                    try{ // here conflict in search @yousef
                        for (Account account : bank.getAccounts()) {
                            if (account.accountNumber.equals(input)) {
                                client = bank.getClientById(account.clientId);
                            }
                        }
                    } catch(IllegalArgumentException ex){
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if (client == null) {
                        JOptionPane.showMessageDialog(this,
                                "No client found with the account number: " + input,
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        displayClientInfo(client);
                    }
                }
            }
        }
    }

    private boolean deleteAccount(String username, String password, String accountNumber) {
        //deletion logic @yousef
        try {
            Client client = bank.getClientByUsername(username);
         //logic el delete ya yousef basha
            return true;
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
    private void displayClientInfo(Client client) {
        JOptionPane.showMessageDialog(this,
                "Client Info: \n" + client.toString(),
                "Client Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public EmployeeWindow(Bank bank, Employee employee) {
        this.bank = bank;
        this.employee = employee;

        setTitle("Employee Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel buttonPanel = getJPanel();
        add(buttonPanel, BorderLayout.NORTH);

        functionOutputArea = new JTextArea();
        functionOutputArea.setEditable(false);
        functionOutputArea.setLineWrap(true);
        functionOutputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(functionOutputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Function Output"));

        add(scrollPane, BorderLayout.CENTER);

        // Add a WindowListener to handle the close button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Save data
                saveData();
                System.exit(0); // Exit the program
            }
        });

        setLocationRelativeTo(null); // Center the window
    }

    private void saveData() {
        try {
            // Save clients and employees to their respective files
            String clientsFilePath = "src/main/data/clients.json";
            String employeesFilePath = "src/main/data/employees.json";

            FileManager.saveToJson(clientsFilePath, bank.getClients());
            FileManager.saveToJson(employeesFilePath, bank.getEmployees());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }



    private void logout() {
        saveData();
        new LoginWindow(bank).setVisible(true);
        this.dispose();
    }
    private JPanel getJPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4,2,5,5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 8, 20));


        JButton[] buttons = {
                new JButton("Edit Personal Information"),
                new JButton("Create Client"),
                new JButton("Create Client Account"),
                new JButton("Search for Client"),
                new JButton("Delete Client Account"),
                new JButton("Edit Client Information"),
                new JButton("Log Out")
        };

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setPreferredSize(new Dimension(200, 40));


            button.addActionListener(e -> {

                if (button.getText().equals("Edit Personal Information")) {
                    functionOutputArea.setText("");
                    editPersonalInfo();

                }else if (button.getText().equals("Create Client Account")) {
                    functionOutputArea.setText("");
                     createClientAcc();
                } else if (button.getText().equals("Create Client")) {
                    functionOutputArea.setText("");
                    createClient();

                } else if (button.getText().equals("Search for Client")) {
                    functionOutputArea.setText("");
                    searchClient();
                } else if (button.getText().equals("Delete Client Account")) {
                    functionOutputArea.setText("");
                    deleteClientAcc();
                } else if (button.getText().equals("Edit Client Information")) {
                    functionOutputArea.setText("");
                    editClientInfo();
                }
                else if (button.getText().equals("Log Out")){
                   logout();
                }
            });

            buttonPanel.add(button);
        }
        return buttonPanel;
    }

}