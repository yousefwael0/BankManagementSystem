package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import services.Bank;
import models.user.Employee;
import models.user.Client;
import models.account.Transaction;
import services.FileManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AdminWindow extends JFrame {
    private Bank bank;

    // UI Components
    private JTable employeeTable;
    private JTable clientTable;
    private JTable transactionTable;
    private JTextField empNameField, empUsernameField, empPasswordField, dateField, clientIDField, employeeIDField;
    private JButton authorizeEmployeeButton, viewEmployeesButton, viewClientsButton, showTransactionsButton, filterButton;

    public AdminWindow(Bank bank) {
        this.bank = bank;
        setTitle("Admin Dashboard");
        setSize(800, 600);  // Set an initial size for the window
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Add a WindowListener to handle the close button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClose();
            }
        });

        // Initialize UI components
        initializeComponents();
    }

    private void initializeComponents() {
        // Panel for employee authorization
        JPanel authPanel = new JPanel(new GridLayout(4, 2));
        authPanel.add(new JLabel("Employee Name:"));
        empNameField = new JTextField();
        authPanel.add(empNameField);

        authPanel.add(new JLabel("Username:"));
        empUsernameField = new JTextField();
        authPanel.add(empUsernameField);

        authPanel.add(new JLabel("Password:"));
        empPasswordField = new JTextField();
        authPanel.add(empPasswordField);

        authorizeEmployeeButton = new JButton("Authorize Employee");
        authorizeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authorizeNewEmployee();
            }
        });
        authPanel.add(authorizeEmployeeButton);

        // Panel for displaying tables (employees, clients, transactions)
        JPanel displayPanel = new JPanel(new GridLayout(2, 2));

        viewEmployeesButton = new JButton("View Employees");
        viewEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayEmployees();
            }
        });
        displayPanel.add(viewEmployeesButton);

        viewClientsButton = new JButton("View Clients");
        viewClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayClients();
            }
        });
        displayPanel.add(viewClientsButton);

        showTransactionsButton = new JButton("Show Transactions");
        showTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactions();
            }
        });
        displayPanel.add(showTransactionsButton);

        // Adding panels to the window
        add(authPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        // Initialize tables for data display
        employeeTable = new JTable();
        clientTable = new JTable();
        transactionTable = new JTable();

        // Wrap each table in a JScrollPane to make them scrollable
        JScrollPane employeeScrollPane = new JScrollPane(employeeTable);
        JScrollPane clientScrollPane = new JScrollPane(clientTable);
        JScrollPane transactionScrollPane = new JScrollPane(transactionTable);

        // Add the tables as a JPanel in the center section
        JPanel tablePanel = new JPanel(new CardLayout());
        tablePanel.add(employeeScrollPane, "Employees");
        tablePanel.add(clientScrollPane, "Clients");
        tablePanel.add(transactionScrollPane, "Transactions");
        add(tablePanel, BorderLayout.SOUTH);
    }
    private void handleClose() {
        // Confirm before exiting
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Do you want to save your data before exiting?",
                "Confirm Exit",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            // Save data
            saveData();
            System.exit(0); // Exit the program
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0); // Exit without saving
        }
        // Cancel option does nothing
    }
    private void saveData() {
        try {
            // Save clients and employees to their respective files
            String clientsFilePath = "src/main/data/clients.json";
            String employeesFilePath = "src/main/data/employees.json";

            FileManager.saveToJson(clientsFilePath, bank.getClients());
            FileManager.saveToJson(employeesFilePath, bank.getEmployees());

            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to authorize new employee
    private void authorizeNewEmployee() {
        String name = empNameField.getText();
        String username = empUsernameField.getText();
        String password = empPasswordField.getText();

        // Basic validation (can be extended)
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try{
            bank.authorizeEmployee(bank.getEmployeeByUsername(username));
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(this, "Employee authorized successfully.");
    }

    // Method to display all employees
    private void displayEmployees() {
        List<Employee> employees = bank.getEmployees();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Employee ID");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Active");

        for (Employee employee : employees) {
            model.addRow(new Object[]{employee.userId, employee.getFirstName() + " " + employee.getLastName(), employee.getPosition(), employee.isActive()});
        }

        employeeTable.setModel(model);
        updateTableView("Employees");
    }

    // Method to display all clients
    private void displayClients() {
        List<Client> clients = bank.getClients();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Client ID");
        model.addColumn("Name");
        model.addColumn("Phone Number");

        for (Client client : clients) {
            model.addRow(new Object[]{client.userId, client.getFirstName() + " " + client.getLastName(), client.getPhoneNumber()});
        }

        clientTable.setModel(model);
        updateTableView("Clients");
    }

    // Method to show all transactions
    private void showTransactions() {
        List<Transaction> transactions = bank.getTransactions(); // Modify bank to return all transactions
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Transaction ID");
        model.addColumn("Amount");
        model.addColumn("Type");
        model.addColumn("Date");
        model.addColumn("Client ID");
        model.addColumn("Employee ID");

        for (Transaction transaction : transactions) {
            model.addRow(new Object[]{
                    transaction.getTransactionId(),
                    transaction.getAmount(),
                    transaction.getType(),
                    transaction.getDate(),
                    //transaction.getClientID(),
                    transaction.getEmployeeId()
            });
        }

        transactionTable.setModel(model);
        updateTableView("Transactions");
    }

    // Method to switch between table views (employees, clients, transactions)
    private void updateTableView(String view) {
        CardLayout cardLayout = (CardLayout) ((JPanel) getContentPane().getComponent(2)).getLayout();
        cardLayout.show((JPanel) getContentPane().getComponent(2), view);
    }
}