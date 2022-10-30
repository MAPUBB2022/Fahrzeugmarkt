package model;

public class Buyer extends User
{
    private int carsBought;

    public Buyer(String username, String password, String location)
    {
        super(username, password, location);
        carsBought = 0;
    }

    public int getCarsBought() {
        return carsBought;
    }
}
