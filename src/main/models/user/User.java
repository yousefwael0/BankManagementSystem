package models.user;

public abstract class User {

    // Attributes
    public final int userId;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;
    private static int counter = 1;

    // Constructors
    public User(String firstName,String lastName,String username,String password){
        this.userId=counter;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.username=username;
        this.password=password;
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
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public static int getCounter() {
        return counter;
    }
    public static void decreaseCounter(){
        if (counter > 1) {
            counter--;
        } else {
            System.out.println("Counter cannot be decreased further.");
        }
    }

    // Methods
    public abstract void editUserInfo(String... args);
}
