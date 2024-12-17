package gui;

import javax.swing.*;
import services.Bank;
import models.user.Employee;
import models.user.Client;

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

    private JPasswordField makePasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        return passwordField;
    }

    private void createClientAcc() {
        JTextField firstNameField = makeTextField();
        JTextField lastNameField = makeTextField();
        JTextField usernameField = makeTextField();
        JPasswordField passwordField = makePasswordField();
        JTextField phoneNumberField = makeTextField();

        Object[] fields = {
                "Enter client's first name:", firstNameField,
                "Enter client's last name:", lastNameField,
                "Enter client's username:", usernameField,
                "Enter client's password:", passwordField,
                "Enter client's phone number:", phoneNumberField
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
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String phoneNumber = phoneNumberField.getText();

                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "All fields must be filled in. Please provide valid information for all fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    try {
                        Client newClient = new Client(firstName, lastName, username, password, phoneNumber);
                        //el logic bta3 el add account. @yousef

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
        JTextField usernameField = makeTextField();
        JPasswordField passwordField = makePasswordField();
        Object[] fields2 = {
                "Enter client's username:", usernameField,
                "Enter client's password:", passwordField,

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

                if (username.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(this,
                            "please provide information for all fields",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    boolean accountDeleted = deleteAccount(username, password);
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

    private boolean deleteAccount(String username, String password) {
        //deletion logic @yousef
        String storedUsername = "nour";
        String storedPassword = "nour1234";
        return username.equals(storedUsername) && password.equals(storedPassword);
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

        setLocationRelativeTo(null); // Center the window
    }


    private JPanel getJPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2,2,5,5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 8, 20));


        JButton[] buttons = {
                new JButton("Edit Personal Information"),
                new JButton("Create Client Account"),
                new JButton("Search for Client"),
                new JButton("Delete Client Account")
        };

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setPreferredSize(new Dimension(200, 40));


            button.addActionListener(e -> {

                if (button.getText().equals("Edit Personal Information")) {
                    functionOutputArea.setText("");
                    functionOutputArea.append("Edit info selected\n");
                    // Implement edit information logic
                }

                else if (button.getText().equals("Create Client Account")) {
                    functionOutputArea.setText("");
                    createClientAcc();
                }

                else if (button.getText().equals("Search for Client")) {
                    functionOutputArea.setText("");
                    functionOutputArea.append("Search selected\n");
                    // Implement client search logic
                }

                else if (button.getText().equals("Delete Client Account")) {
                    functionOutputArea.setText("");
                    functionOutputArea.append("Delete Client selected\n");
                    deleteClientAcc();
                }
            });

            buttonPanel.add(button);
        }

        return buttonPanel;
    }
}