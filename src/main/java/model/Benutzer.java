package model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    public void update (Benutzer other)
    {
        this.setPassword(other.getPassword());
        this.setLocation(other.getLocation());
        if(this instanceof Seller && other instanceof Seller)
        {
            ((Seller) this).setRating(((Seller) other).getRating());
        }
        else if (this instanceof Buyer && other instanceof Buyer)
        {
            ((Buyer) this).setCarsBought(((Buyer)other).getCarsBought());
        }
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
