package model;

import javax.persistence.Entity;

@Entity
public class Buyer extends Benutzer
{
    private int carsBought;

    public Buyer(String username, String password, String location)
    {
        super(username, password, location);
        carsBought = 0;
    }

    public Buyer() {

    }

    public int getCarsBought() {
        return carsBought;
    }

    public void setCarsBought(int carsBought) {
        this.carsBought = carsBought;
    }
}
