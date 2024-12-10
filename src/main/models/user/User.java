package models.user;

public abstract class User {
    private int ID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public User(int ID, String firstName, String lastName, String username, String password) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract void displayMenu();
}