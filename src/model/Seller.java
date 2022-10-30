package model;

public class Seller extends User
{
    private double rating;
    public Seller(String username, String password, String location) {
        super(username, password, location);
        rating = 5.0d;
    }

    public double getRating() {
        return rating;
    }
}
