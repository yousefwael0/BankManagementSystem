package models.user;

import java.util.Map;

public class Employee extends User{
    // Unique Attributes
    private boolean isActive = false;
    private String address;
    private String position;
    public String graduatedCollege;
    public String totalGrade;
    public int  yearOfGraduation;

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
    public Employee() {
        super();
        this.address = "ADDRESS";
        this.position = "POSITION";
        this.totalGrade = "GRADE";
        this.graduatedCollege = "COLLEGE OF GRADE";
        this.yearOfGraduation = 2000;
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
}
