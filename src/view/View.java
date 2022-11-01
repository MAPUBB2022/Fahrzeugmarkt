package view;

import controller.Controller;
import model.Advert;
import model.Car;
import model.Motorcycle;

import java.util.Scanner;

public class View {
    private Controller controller;

    private int userMode;

    public View() {
        this.controller = new Controller();
        userMode = -1;
    }

    public void login()
    {

    }

    public void mainMenu()
    {

    }

    public Advert createAd(){
        boolean is_Car;
        Advert a;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Car/Motocycle");
        is_Car=Boolean.parseBoolean(myObj.nextLine());

        System.out.println("make:");
        String make = myObj.nextLine();
        System.out.println("model:");
        String model = myObj.nextLine();
        System.out.println("year:");
        int year = Integer.parseInt(myObj.nextLine());
        System.out.println("displacement:");
        int displacement = Integer.parseInt(myObj.nextLine());
        System.out.println("hp:");
        int hp = Integer.parseInt(myObj.nextLine());
        System.out.println("torque:");
        int torque = Integer.parseInt(myObj.nextLine());
        System.out.println("used:");
        boolean used= Boolean.parseBoolean(myObj.nextLine());
        System.out.println("automatic gearbox:");
        boolean automaticGearbox= Boolean.parseBoolean(myObj.nextLine());


        if(is_Car){
            int nrDoors=Integer.parseInt(myObj.nextLine());
            int nrSeats=Integer.parseInt(myObj.nextLine());
            a = new Car(make, model, year,displacement,hp,torque,used,automaticGearbox,nrDoors,nrSeats);
        }
        else
        {
            String suspensionType = myObj.nextLine();
            String brakeType = myObj.nextLine();
            a = new Motorcycle(make, model, year,displacement,hp,torque,used,automaticGearbox,suspensionType,brakeType);
        }
        return a;
    }

}
