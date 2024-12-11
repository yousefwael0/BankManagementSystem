package models.user;

import java.util.Map;

public class Employee extends User{
    // Unique Attributes
    private boolean isActive = false;
    private String address;
    private String position;
    public final String graduatedCollege;
    public final String totalGrade;
    public final int  yearOfGraduation;

    // Constructor
    public Employee(String firstName, String lastName, String address, String position, String username, String password,
                    String graduatedCollege, int yearOfGraduation, String totalGrade) {
        super(firstName, lastName, username, password);
        this.setAddress(address);
        this.setPosition(position);

        // Setting the grade
        if (totalGrade == null || totalGrade.trim().isEmpty())
            throw new IllegalArgumentException("Total Grade cannot be empty!");
        this.totalGrade = totalGrade;

        // Setting the College
        if (graduatedCollege == null || graduatedCollege.trim().isEmpty())
            throw new IllegalArgumentException("Graduated College cannot be empty!");
        this.graduatedCollege = graduatedCollege;

        // Setting the year of graduation
        if (yearOfGraduation < 1900 || yearOfGraduation > 2023)
            throw new IllegalArgumentException("Year of Graduation must be between 1900 and 2023!");
        this.yearOfGraduation = yearOfGraduation;
    }

    // Getters
    public String getAddress() {return address;}
    public String getPosition() {return position;}
    public boolean isActive() {return isActive;}

    // Setters
    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty())
            throw new IllegalArgumentException("Address cannot be empty!");
        this.address = address;
    }
    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty())
            throw new IllegalArgumentException("Position cannot be empty!");
        this.position = position;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    // Methods
    @Override
    public void editUserInfo(Map<String, String> changes) {
        // Only allow address and position to be updated
        if (changes.containsKey("address")) {
            this.setAddress(changes.get("address"));
        }
        if (changes.containsKey("position")) {
            this.setPosition(changes.get("position"));
        }
        // Ensure no other fields can be edited by the employee
        for (String key : changes.keySet()) {
            if (!key.equals("address") && !key.equals("position")) {
                throw new IllegalArgumentException("Employee can only edit address and position.");
            }
        }
    }
    @Override
    public String toString() {
        return "Employee ID: " + userId + ", Name: " + firstName + " " + lastName +
                ", Address: " + address + ", Position: " + position;
    }

    // Commented to implement in Bank Class (Bank handles all Lists and additions)
    /*static class EmployeeManager {
        private List<Employee> employees = new ArrayList<>();

        public void addEmployee(Employee employee) {
            employees.add(employee);
        }

        public void editEmployee(String empId, String address, String position) throws EmployeeException {
            Employee employee = findEmployeeById(empId);
            if (employee == null) {
                throw new EmployeeException("Employee with ID " + empId + " not found!");
            } else {
                employee.editInfo(address, position);
            }
        }
        public void listEmployees() {
            if (employees.isEmpty()) {
                System.out.println("No employees available.");
            } else {
                employees.forEach(System.out::println);
            }
        }

        private Employee findEmployeeById(String empId) {
            for (Employee employee : employees) {
                if (employee.getEmpId() .equals(empId) ) {
                    return employee;
                }
            }
            return null;
        }
    }
    static class EmployeeException extends Exception {
        public EmployeeException(String message) {
            super(message);
        }
    }*/
}
