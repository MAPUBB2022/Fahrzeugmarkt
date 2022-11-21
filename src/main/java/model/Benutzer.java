package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Benutzer
{
    @Id
    private String username;

    private String password, location;

    public Benutzer(String username, String password, String location) {
        this.username = username;
        this.password = password;
        this.location = location;
    }

    public Benutzer() {

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
