package models.user;

import java.util.Map;

public abstract class User {

    // Attributes
    public final String userId;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;
    private static int counter = 1;

    // Constructors
    public User(String firstName,String lastName,String username,String password){
        this.userId=Integer.toString(counter);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUsername(username);
        this.setPassword(password);
        counter++;
    }
    public User(){
        this("FirstName", "LastName", "username", "password");
    }

    // getters and setters
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("last name cannot be empty.");
        }
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("user name cannot be empty.");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        this.password = password;
    }

    public static int getCounter() {
        return counter;
    }
    public static void setCounter(int counter) {
        User.counter = counter;
    }
    public static void decreaseCounter(){
        if (counter > 1) {
            counter--;
        } else {
            System.out.println("Counter cannot be decreased further.");
        }
    }

    // Methods
    public abstract void editUserInfo(Map<String, String> changes);
}
