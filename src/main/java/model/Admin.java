package model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="username")
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
