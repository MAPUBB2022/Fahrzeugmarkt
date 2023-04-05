package model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="ID")
public class Car extends Advert {

    private int nrDoors, nrSeats;

    public Car(Seller seller, int auctionDays, String make, String model, int year, int displacement, int hp, int torque, boolean used, boolean automaticGearbox, int nrDoors, int nrSeats, int buyPrice, int startPrice) {
        super(seller, auctionDays, make, model, year, displacement, hp, torque, used, automaticGearbox, buyPrice, startPrice);
        this.nrDoors = nrDoors;
        this.nrSeats = nrSeats;

    }

    public Car() {

    }


    public int getNrDoors() {
        return nrDoors;
    }

    public void setNrDoors(int nrDoors) {
        this.nrDoors = nrDoors;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(int nrSeats) {
        this.nrSeats = nrSeats;
    }
}
