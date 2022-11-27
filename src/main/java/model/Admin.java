package model;

import javax.persistence.Entity;

@Entity
public class Admin extends Benutzer
{
    public Admin(String username, String password, String location)
    {
        super(username, password, location);
    }
    public Admin()
    {
    }
}
