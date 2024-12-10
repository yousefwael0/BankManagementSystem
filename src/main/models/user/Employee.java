package models.user;

public class Employee extends User {
    private String address;
    private String position;
    private String graduatedCollege;
    private int yearOfGraduation;
    private String totalGrade;

    public Employee(int ID, String firstName, String lastName, String username, String password, String address,
                    String position, String graduatedCollege, int yearOfGraduation, String totalGrade) {
        super(ID, firstName, lastName, username, password);
        this.address = address;
        this.position = position;
        this.graduatedCollege = graduatedCollege;
        this.yearOfGraduation = yearOfGraduation;
        this.totalGrade = totalGrade;
    }

    // Getters and setters for attributes
    public void editPersonalInfo(String address, String position) {
        this.address = address;
        this.position = position;
    }

    @Override
    public void displayMenu() {
        System.out.println("Welcome, " + getFirstName());
        System.out.println("1. Create Client Account");
        System.out.println("2. Edit Client Account");
        System.out.println("3. Search Client");
        System.out.println("4. Delete Client Account");
        System.out.println("5. Logout");
    }
}