package model;

public abstract class Advert
{

    private Seller seller;

    private String make, model;
    private int year, displacement, hp, torque, startPrice, buyPrice,ID;
    private boolean used, automaticGearbox;

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Advert(String make, String model, int year, int displacement, int hp, int torque, boolean used, boolean automaticGearbox) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.displacement = displacement;
        this.used = used;
        this.automaticGearbox = automaticGearbox;
        this.hp = hp;
        this.torque = torque;
        this.ID=-1;
    }

    public boolean isAutomaticGearbox() {
        return automaticGearbox;
    }

    public void setAutomaticGearbox(boolean automaticGearbox) {
        this.automaticGearbox = automaticGearbox;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque =torque;
}
}