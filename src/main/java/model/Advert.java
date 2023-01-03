package model;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Advert
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ID;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "seller_id"
    )
    private Seller seller;
    LocalDate placeDate; // date when the ad was put up
    private int auctionDays; // 0 for no auction
    private String make, model;
    private int year, displacement, hp, torque, startPrice, buyPrice;
    private boolean used, automaticGearbox;

    public Advert() {

    }

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

    public Advert(Seller seller, int auctionDays, String make, String model, int year, int displacement, int hp, int torque, boolean used, boolean automaticGearbox, int buyPrice, int startPrice)
    {
        this.seller = seller;
        this.make = make;
        this.model = model;
        this.year = year;
        this.displacement = displacement;
        this.used = used;
        this.automaticGearbox = automaticGearbox;
        this.hp = hp;
        this.torque = torque;
        this.auctionDays = auctionDays;
        this.buyPrice = buyPrice;
        this.startPrice = startPrice;
        /////////////////
        placeDate = LocalDate.now();
    }

    public void update(Advert other)
    {
        this.setSeller(other.getSeller());
        this.setMake(other.getMake());
        this.setModel(other.getModel());
        this.setYear(other.getYear());
        this.setDisplacement(other.getDisplacement());
        this.setUsed(other.isUsed());
        this.setAutomaticGearbox(other.isAutomaticGearbox());
        this.setHp(other.getHp());
        this.setTorque(other.getTorque());
        this.setAuctionDays(other.getAuctionDays());
        this.setStartPrice(other.getStartPrice());
        this.setBuyPrice(other.getBuyPrice());
        this.setPlaceDate(other.getPlaceDate());
        if(this instanceof Car && other instanceof Car)
        {
            ((Car) this).setNrDoors(((Car)other).getNrDoors());
            ((Car) this).setNrSeats(((Car)other).getNrSeats());
        }
        else if(this instanceof Motorcycle && other instanceof Motorcycle)
        {
            ((Motorcycle) this).setSuspensionType(((Motorcycle)other).getSuspensionType());
            ((Motorcycle) this).setBrakeType(((Motorcycle)other).getBrakeType());
        }
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public LocalDate getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(LocalDate placeDate) {
        this.placeDate = placeDate;
    }

    public int getAuctionDays() {
        return auctionDays;
    }

    public void setAuctionDays(int auctionDays) {
        this.auctionDays = auctionDays;
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