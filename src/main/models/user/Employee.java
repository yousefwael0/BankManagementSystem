package models.user;

public class Employee extends User{
    // Unique Attributes
    private String address;
    private String position;
    private String graduatedCollege;
    private String totalGrade;
    private int  yearOfGraduation;

    // Constructor
    public Employee(String firstName, String lastName, String address, String position, String username, String password,
                    String graduatedCollege, int yearOfGraduation, String totalGrade) {
        super(firstName, lastName, username, password);
        this.setAddress(address);
        this.setPosition(position);
    }

    // Getters
    public String getAddress() {return address;}
    public String getPosition() {
        return position;
    }
    public String getTotalGrade() {
        return totalGrade;
    }
    public int getYearOfGraduation() {
        return yearOfGraduation;
    }
    public String getGraduatedCollege() {
        return graduatedCollege;
    }

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
    public void setTotalGrade(String totalGrade) {
        if (totalGrade == null || totalGrade.trim().isEmpty())
            throw new IllegalArgumentException("Total Grade cannot be empty!");
        this.totalGrade = totalGrade;
    }
    public void setGraduatedCollege(String graduatedCollege) {
        if (graduatedCollege == null || graduatedCollege.trim().isEmpty())
            throw new IllegalArgumentException("Graduated College cannot be empty!");
        this.graduatedCollege = graduatedCollege;
    }
    public void setYearOfGraduation(int yearOfGraduation) {
        if (yearOfGraduation < 1900 || yearOfGraduation > 2023)
            throw new IllegalArgumentException("Year of Graduation must be between 1900 and 2023!");
        this.yearOfGraduation = yearOfGraduation;
    }

    @Override
    public void editUserInfo(String... args) {

        if (address != null) this.address = args[0];
        if (position != null) this.position = args[1];
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
