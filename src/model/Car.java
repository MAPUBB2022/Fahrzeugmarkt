package model;

public class Car extends Advert {
    private int nrDoors, nrSeats;


    public Car(int auctionDays, String make, String model, int year, int displacement, int hp, int torque, boolean used, boolean automaticGearbox,int nrDoors,int nrSeats) {
        super(auctionDays, make, model, year, displacement, hp, torque, used, automaticGearbox);
        this.nrDoors = nrDoors;
        this.nrSeats = nrSeats;
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
