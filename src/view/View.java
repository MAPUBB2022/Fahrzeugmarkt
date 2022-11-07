package view;

import controller.Controller;
import model.*;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import java.util.Scanner;

public class View {
    private Controller controller;
    private UserRepository userRepository;
    private AdsRepository adsRepository;
    private TransactionRepository transactionRepository;
    private int userMode;

    public View(Controller controller, UserRepository userRepository, AdsRepository adsRepository, TransactionRepository transactionRepository) {
        this.controller = controller;
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.transactionRepository = transactionRepository;
        this.userMode = -1;
    }

    public void login()
    {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username=myObj.nextLine();

        System.out.println("Enter password: ");
        String password=myObj.nextLine();

        userMode = controller.checkCreds(username,password);
    }

    public void mainMenu()
    {
        while (true) {
            if (userMode == -1)
            {
                System.out.println("Login failed! Try again");
                login();
            }

            if(userMode==0)//Admin
            {


            }
            if(userMode==1){//Buyer

            }
            if(userMode==2){//Seller

            }

        }
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

        int endDate = 0;

        if(is_Car){
            int nrDoors=Integer.parseInt(myObj.nextLine());
            int nrSeats=Integer.parseInt(myObj.nextLine());
            a = new Car(endDate, make, model, year,displacement,hp,torque,used,automaticGearbox,nrDoors,nrSeats);
        }
        else
        {
            String suspensionType = myObj.nextLine();
            String brakeType = myObj.nextLine();
            a = new Motorcycle(endDate, make, model, year,displacement,hp,torque,used,automaticGearbox,suspensionType,brakeType);
        }
        return a;
    }

}
