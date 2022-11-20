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

    private void printAd(Advert advert)
    {
        int type = 0;
        if(advert instanceof Car)
        {
            type = 1;
            System.out.println("\n\nCar\n");
        }

        if(advert instanceof Motorcycle)
        {
            type = 2;
            System.out.println("\n\nMotorcycle\n");
        }
        System.out.printf("Make:\t%s\nModel:\t%s\n", advert.getMake(), advert.getModel());
        System.out.printf("Year:\t%s\nDisplacement:\t%s cc\n", advert.getYear(), advert.getDisplacement());
        System.out.printf("HP:\t%s\nTorque:\t%s Nm\n", advert.getHp(), advert.getTorque());
        System.out.printf("Used:\t%s\nAutomatic:\t%s\n", advert.isUsed(), advert.isAutomaticGearbox());
        System.out.printf("Created:\t%s\nDays:\t%s\n", advert.getPlaceDate(), advert.getAuctionDays());
        System.out.printf("Start Price:\t%s\nBuy Price:\t%s\n", advert.getStartPrice(), advert.getBuyPrice());


    }

    public void login()
    {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = myObj.nextLine();

        System.out.println("Enter password: ");
        String password = myObj.nextLine();

        userMode = controller.checkCreds(username, password);
    }

    public void mainMenu()
    {
        while (true) {
            if (userMode == -1)
            {
                login();
                if(userMode!=-1)
                    continue;
                else
                    System.out.println("Login failed! Try again");
            }

            if(userMode==0)//Admin
            {


            }
            if(userMode==1){//Buyer
                System.out.print("\n\nHello Buyer\n\nWhat do you want to do?\n\n1. See all ads from today\n2. See all ads\n\n Choose an option (1-2): ");
                Scanner myObj = new Scanner(System.in);
                int op = myObj.nextInt();
                if(op == 1)
                {
                    for (Advert a: adsRepository.getAllAdsFromToday() ) {
                        printAd(a);
                    }
                }
                else if (op == 2)
                {
                    for (Advert a: adsRepository.findAll() ) {
                        printAd(a);
                    }
                }
                else
                    System.out.print("Invalid selection. Please try again");
            }
            if(userMode==2)
            {//Seller

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

        System.out.println("automatic gearbox:");
        int buyPrice = Integer.parseInt(myObj.nextLine());

        System.out.println("automatic gearbox:");
        int startPrice = Integer.parseInt(myObj.nextLine());

        int endDate = 0;

        if(is_Car){
            System.out.println("Number of doors:");
            int nrDoors=Integer.parseInt(myObj.nextLine());
            System.out.println("Number of seats:");
            int nrSeats=Integer.parseInt(myObj.nextLine());
            a = new Car(endDate, make, model, year,displacement,hp,torque,used,automaticGearbox,nrDoors,nrSeats, buyPrice, startPrice);
        }
        else
        {
            System.out.println("suspension type:");
            String suspensionType = myObj.nextLine();
            System.out.println("brake type:");
            String brakeType = myObj.nextLine();
            a = new Motorcycle(endDate, make, model, year,displacement,hp,torque,used,automaticGearbox,suspensionType,brakeType, buyPrice, startPrice);
        }
        return a;
    }

}
