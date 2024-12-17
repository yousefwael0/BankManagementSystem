package models.user;

import java.util.Map;

public class Admin extends User{
    // Constructor
    public Admin(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.username = "admin";
        this.password = "admin";
    }

    @Override
    public void editUserInfo(Map<String, String> changes) {
        // Update any field provided in the changes map
        if (changes.containsKey("firstName")) {
            this.setFirstName(changes.get("firstName"));
        }
        if (changes.containsKey("lastName")) {
            this.setLastName(changes.get("lastName"));
        }
    }
}