package edu.upc.dsa.models.bodies;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class LoginCredentials {
    String username;
    String password;

    public LoginCredentials(){}
    // full constructor
    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //all getters and setters from attributes of User class
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

}
