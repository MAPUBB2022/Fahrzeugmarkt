package model;

public class Motorcycle extends Advert
{
    private String suspensionType, brakeType;


    public Motorcycle(String make, String model, int year, int displacement, int hp, int torque, boolean used, boolean automaticGearbox, String suspensionType, String brakeType) {
        super(make, model, year, displacement, hp, torque, used, automaticGearbox);
        this.brakeType = brakeType;
        this.suspensionType = suspensionType;
    }

    public String getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(String suspensionType) {
        this.suspensionType = suspensionType;
    }

    public String getBrakeType() {
        return brakeType;
    }

    public void setBrakeType(String brakeType) {
        this.brakeType = brakeType;
    }
}
