package view;

import controller.Controller;
import model.*;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class View {
    private Controller controller;
    private UserRepository userRepository;
    private AdsRepository adsRepository;
    private TransactionRepository transactionRepository;

    private User loggedUser;

    public View(Controller controller, UserRepository userRepository, AdsRepository adsRepository, TransactionRepository transactionRepository) {
        this.controller = controller;
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.transactionRepository = transactionRepository;
        //////////////////////////////
        this.loggedUser = null;
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

        // TODO UNFINISHED
    }

    private void printAdSummary(Advert advert)
    {
        if(advert instanceof Car)
            System.out.print("Car\t");

        if(advert instanceof Motorcycle)
            System.out.print("Motorcycle\t");

        System.out.printf("%s %s %s; %s Euro\n", advert.getMake(), advert.getModel(), advert.getYear(),advert.getBuyPrice());
    }

    public void login()
    {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = myObj.nextLine();

        System.out.println("Enter password: ");
        String password = myObj.nextLine();

        loggedUser = controller.checkCreds(username, password);
    }

    private void presentAdsBuyer(List<Advert> adList)
    {
        int globalCounter=0;
        int i=0;
        while(globalCounter<adList.size())
        {
            for(i=0; i<10 && i+globalCounter < adList.size(); i++) // "Page size" is 10
            {
                System.out.printf("%d\t", i+1);
                printAdSummary(adList.get(globalCounter+i));
            }
            System.out.printf("Select a car (1-%s) or press enter to see the next page: ",i);

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            String userInput = myObj.nextLine();
            if(Objects.equals(userInput, ""))
            {
                globalCounter+=i;
                continue;
            }
            int conve = Integer.parseInt(userInput);
            if(conve>=1 && conve<=10)
            {
                //present ad in a detailed fashion
                Advert currentAd = adList.get(globalCounter+conve-1);
                printAd(currentAd);
                System.out.printf("\nThe current bid on this Advert is %s Euro\n", controller.getCurrentBid(currentAd));
                System.out.println("What would you like to do: \n1. Place a bid\n2. Buy the car\n\nChoose an option (1-2): ");
                int opt = myObj.nextInt();
                if(opt==1)
                {
                    System.out.println("How much would you like to bid? ");
                    int amount = myObj.nextInt();
                    Transaction transaction = new Transaction((Buyer) loggedUser, currentAd, amount, true);
                    controller.placeBid(transaction);
                }
                else if (opt==2)
                {
                    Transaction transaction = new Transaction((Buyer) loggedUser, currentAd, currentAd.getBuyPrice(), false);
                    controller.buyUpfront(transaction);
                    System.out.println("Transaction completed successfully!");
                }
                break;
            }

        }
    }

    private void presentAdsSeller(List<Advert> adList)
    {
        for (Advert a: adList )
        {
            printAdSummary(a);
            System.out.printf("\tCurrent bid: %s\n\n", controller.getCurrentBid(a));
        }
    }

    private void presentTransactions(List<Transaction> transactions)
    {

    }

    public void mainMenu()
    {
        while (true) {
            if (loggedUser == null)
            {
                login();
                if(loggedUser != null)
                    continue;
                else
                    System.out.println("Login failed! Try again");
            }

            if(loggedUser instanceof Admin)//Admin
            {
                // TODO

            }
            if(loggedUser instanceof Buyer)
            {
                System.out.print("\n\nHello Buyer\n\nWhat do you want to do?\n\n1. See all ads from today\n2. See all ads\n\n Choose an option (1-2): ");
                Scanner myObj = new Scanner(System.in);
                int op = myObj.nextInt();
                if(op == 1)
                {
                    System.out.println("\nCars listed today:\n");
                    presentAdsBuyer(adsRepository.getAllAdsFromToday());
                }
                else if (op == 2)
                {
                    System.out.println("\nComplete car list:\n");
                    presentAdsBuyer(adsRepository.findAll());
                }
                else
                    System.out.print("Invalid selection. Please try again");
            }
            if(loggedUser instanceof Seller)
            {
                System.out.print("\n\nHello Seller\n\nWhat do you want to do?\n\n1. Create an advert\n2. See your adverts\n3. See pending transactions\n\n Choose an option (1-3): ");
                Scanner myObj = new Scanner(System.in);
                int op = myObj.nextInt();
                if(op == 1)
                {
                    createAd();
                }
                else if (op == 2)
                {
                    System.out.println("\nYour adverts:\n");
                    presentAdsSeller(adsRepository.getAllAdsFromSeller((Seller) loggedUser));
                }
                else if (op == 3)
                {
                    System.out.println("\nYour pending transactions:\n");
                    presentTransactions(transactionRepository.getTransactionsBySeller((Seller) loggedUser));
                }
                else
                    System.out.print("Invalid selection. Please try again");
            }
        }
    }

    public void createAd(){
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

        int endDate = 0; //  TODO nu e bine

        if(is_Car){
            System.out.println("Number of doors:");
            int nrDoors=Integer.parseInt(myObj.nextLine());
            System.out.println("Number of seats:");
            int nrSeats=Integer.parseInt(myObj.nextLine());
            a = new Car((Seller) loggedUser, endDate, make, model, year,displacement,hp,torque,used,automaticGearbox,nrDoors,nrSeats, buyPrice, startPrice);
        }
        else
        {
            System.out.println("suspension type:");
            String suspensionType = myObj.nextLine();
            System.out.println("brake type:");
            String brakeType = myObj.nextLine();
            a = new Motorcycle( (Seller) loggedUser, endDate, make, model, year,displacement,hp,torque,used,automaticGearbox,suspensionType,brakeType, buyPrice, startPrice);
        }
        controller.sellCar(a);
    }

}
