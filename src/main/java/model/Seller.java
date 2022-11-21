package model;

import javax.persistence.Entity;

@Entity
public class Seller extends Benutzer
{
    private double rating;
    public Seller(String username, String password, String location) {
        super(username, password, location);
        rating = 5.0d;
    }

    public Seller() {

    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
}
