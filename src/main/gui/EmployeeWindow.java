package gui;

import javax.swing.*;
import services.Bank;
import models.user.Employee;
import models.user.Client;
import java.awt.*;
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
                String interest_rate = interestRfield.getText();

                if (username.isEmpty() || password.isEmpty() || accountType == null || balance.isEmpty() || interest_rate.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "All fields must be filled in. Please provide valid information for all fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    try {
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