package view;

import controller.Controller;
import model.*;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class View
{
    private Controller controller;
    private UserRepository userRepository;
    private AdsRepository adsRepository;
    private TransactionRepository transactionRepository;

    private Benutzer loggedBenutzer;

    public View(Controller controller, UserRepository userRepository, AdsRepository adsRepository, TransactionRepository transactionRepository) {
        this.controller = controller;
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.transactionRepository = transactionRepository;
        //////////////////////////////
        this.loggedBenutzer = null;
    }

    private void printAd(Advert advert)
    {
        if(advert instanceof Car)
        {
            System.out.println("\n\nCar\n");
        }

        if(advert instanceof Motorcycle)
        {
            System.out.println("\n\nMotorcycle\n");
        }
        System.out.printf("Seller:\t%s\n", advert.getSeller().getUsername());
        System.out.printf("Make:\t%s\nModel:\t%s\n", advert.getMake(), advert.getModel());
        System.out.printf("Year:\t%s\nDisplacement:\t%s cc\n", advert.getYear(), advert.getDisplacement());
        System.out.printf("HP:\t%s\nTorque:\t%s Nm\n", advert.getHp(), advert.getTorque());
        System.out.printf("Used:\t%s\nAutomatic:\t%s\n", advert.isUsed(), advert.isAutomaticGearbox());
        System.out.printf("Created:\t%s\nDays:\t%s\n", advert.getPlaceDate(), advert.getAuctionDays());
        System.out.printf("Start Price:\t%s\nBuy Price:\t%s\n", advert.getStartPrice(), advert.getBuyPrice());

        if(advert instanceof Car)
        {
            System.out.printf("No. of doors:\t%s\nNo. of seats:\t%s\n", ((Car)advert).getNrDoors(), ((Car)advert).getNrSeats());
        }
        if(advert instanceof Motorcycle)
        {
            System.out.printf("Suspension type:\t%s\nBrake type:\t%s\n", ((Motorcycle)advert).getSuspensionType(), ((Motorcycle)advert).getBrakeType());
        }
    }

    private void printAdSummary(Advert advert)
    {
        if(advert instanceof Car)
            System.out.print("Car\t");

        if(advert instanceof Motorcycle)
            System.out.print("Motorcycle\t");

        System.out.printf("%s %s %s; %s Euro\n", advert.getMake(), advert.getModel(), advert.getYear(),advert.getBuyPrice());
    }
    private void printTransaction(Transaktion transaktion)
    {
        if(transaktion.isBid())
            System.out.printf("\t%s, %s: bid %s Euro\n", transaktion.getBuyer().getUsername(), transaktion.getDate(), transaktion.getAmount());
        else
            System.out.printf("\t%s, %s: bought with %s Euro\n", transaktion.getBuyer().getUsername(), transaktion.getDate(), transaktion.getAmount());
    }
    public void login()
    {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = myObj.nextLine();

        System.out.println("Enter password: ");
        String password = myObj.nextLine();

        loggedBenutzer = controller.checkCreds(username, password);
    }

    private void presentAdsBuyer(List<Advert> adList)
    {
        adList = adList.stream().filter(n -> !controller.isElapsed(n) && !controller.isSold(n)).collect(Collectors.toList());
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
                Transaktion currentBid =  controller.getCurrentBid(currentAd);
                if(currentBid !=null)
                    System.out.printf("\nThe current bid on this Advert is %s Euro\n", currentBid.getAmount());
                else
                    System.out.print("\nThere are no bids on this Advert\n");
                System.out.println("What would you like to do: \n1. Place a bid\n2. Buy the car\n\nChoose an option (1-2): ");
                int opt = myObj.nextInt();
                if(opt==1)
                {
                    System.out.println("How much would you like to bid? ");
                    int amount = myObj.nextInt();
                    Transaktion transaktion = new Transaktion((Buyer) loggedBenutzer, currentAd, amount, true);
                    controller.placeBid(transaktion);
                }
                else if (opt==2)
                {
                    Transaktion transaktion = new Transaktion((Buyer) loggedBenutzer, currentAd, currentAd.getBuyPrice(), false);
                    controller.buyUpfront(transaktion);
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
            if(controller.isElapsed(a))
                System.out.println("\nThis Advert has expired!");
            if(controller.isSold(a))
                System.out.println("\nThis Advert has been sold!");
            printAdSummary(a);
            Transaktion currentBid = controller.getCurrentBid(a);
            if(currentBid != null)
                System.out.printf("\tCurrent bid: %s\n\n", currentBid.getAmount());
            else
                System.out.print("\tThere is currently no bid\n\n");
        }
    }

    private void presentTransactions()
    {
        List<Advert> adverts = adsRepository.getAllAdsFromSeller((Seller) loggedBenutzer);
        adverts = adverts.stream().filter(n -> controller.isElapsed(n) || controller.isSold(n) ).collect(Collectors.toList());

        int globalCounter=0;
        int i=0;
        while(globalCounter<adverts.size())
        {

            for(i=0; i<10 && i+globalCounter < adverts.size(); i++) // "Page size" is 10
            {
                System.out.printf("%d\t", i+1);
                printAdSummary(adverts.get(globalCounter+i));
                Transaktion transaktion = controller.getCurrentBid(adverts.get(globalCounter+i));
                if (adverts.get(globalCounter+i).getAuctionDays() > 0) {
                    if (!controller.isElapsed(adverts.get(globalCounter+i)))
                        System.out.printf("\tAuction will end on %s\n", controller.getAuctionEndDate(adverts.get(globalCounter+i)));
                    else
                        System.out.print("\tAuction has ended!\n");
                } else
                    System.out.print("\tThis is not an auction\n");
                if (transaktion == null)
                    System.out.println("\tNo bids or buy offers on this advert.\n");
                else
                    printTransaction(transaktion);
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
            Advert currentAd = adverts.get(globalCounter+conve-1);
            if(conve>=1 && conve<=10)
            {
                if(controller.isSold(currentAd))
                {
                    System.out.printf("%s has bought this advert!\n", controller.getCurrentBuyer(currentAd).getBuyer().getUsername());
                    System.out.println("What would you like to do: \n1. Accept the transaction\n2. Deny the transaction\n\nChoose an option (1-2): ");
                    int opt = myObj.nextInt();
                    if(opt==1)
                    {
                        controller.acceptTransaction(controller.getCurrentBuyer(currentAd));
                    }
                    else if (opt==2)
                    {
                        controller.denyTransaction(controller.getCurrentBuyer(currentAd));
                    }
                }
                else if(controller.isElapsed(currentAd))
                {
                    Transaktion currentTransaction = controller.getCurrentBid(currentAd);
                    if(currentTransaction!=null)
                    {
                        System.out.printf("%s has won this advert!\n", controller.getCurrentBid(currentAd).getBuyer().getUsername());
                        System.out.println("What would you like to do: \n1. Accept the transaction\n2. Deny the transaction\n\nChoose an option (1-2): ");
                        int opt = myObj.nextInt();
                        if(opt==1)
                        {
                            controller.acceptTransaction(controller.getCurrentBid(currentAd));
                        }
                        else if (opt==2)
                        {
                            controller.denyTransaction(controller.getCurrentBid(currentAd));
                        }
                    }
                    else
                    {
                        System.out.print("The Advert has expired, and there are no bids or buy offers.\n");
                        System.out.println("What would you like to do: \n1. Remove the advert\n2. Refresh the advert\n\nChoose an option (1-2): ");
                        int opt = myObj.nextInt();
                        if(opt==1)
                        {
                            adsRepository.delete(currentAd.getID());
                        }
                        else if (opt==2)
                        {
                            System.out.println("For how many days would you like the Advert to be active: ");
                            int days = myObj.nextInt();
                            currentAd.setAuctionDays(days);
                            adsRepository.update(currentAd.getID(), currentAd);
                        }
                    }
                }
                break;
            }
        }
    }

    public void removeUser()
    {
        System.out.print("Which user would you like to remove: ");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userInput = myObj.nextLine();
        Benutzer b = userRepository.findId(userInput);
        if(b==null)
        {
            System.out.println("User doesn't exist!");
        }
        else
        {
            userRepository.delete(userInput);
        }
    }

    public void addUser()
    {
        System.out.print("Enter a username: ");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userInput = myObj.nextLine();
        if(userRepository.findId(userInput) == null)
        {
            System.out.println("Enter a password: ");
            String pass = myObj.nextLine();
            System.out.println("Enter your location: ");
            String location = myObj.nextLine();
            System.out.print("What kind of user is this?\n0 - Admin\n1 - Buyer\n2 - Seller\nChoose a type (1-3): ");
            int type = myObj.nextInt();
            Benutzer b;
            if(type == 0)
                b = new Admin(userInput, pass, location);
            else if(type==1)
                b = new Buyer(userInput, pass, location);
            else if(type==2)
                b = new Seller(userInput, pass, location);
            else
                throw new RuntimeException(); // todo de schimbat
            userRepository.add(b);
        }
        else
            System.out.println("Username is taken\n");
    }

    public void mainMenu()
    {
        while (true)
        {
            if (loggedBenutzer == null)
            {
                login();
                if(loggedBenutzer != null)
                    continue;
                else
                    System.out.println("Login failed! Try again");
            }

            if(loggedBenutzer instanceof Admin)
            {
                System.out.print("\n\nHello Admin\n\nWhat do you want to do?\n\n1. Add user\n2. Remove user\n3. Log out\n4. Quit\n\n Choose an option (1-4): ");
                Scanner myObj = new Scanner(System.in);
                int op = myObj.nextInt();
                if(op == 1)
                {
                    addUser();
                }
                else if (op == 2)
                {
                    removeUser();
                }
                else if (op == 3)
                {
                    System.out.println("\nLogging out\n");
                    loggedBenutzer=null;
                }
                else if (op == 4)
                {
                    System.out.println("\nQuitting\n");
                    break;
                }
                else
                    System.out.print("Invalid selection. Please try again");

            }
            if(loggedBenutzer instanceof Buyer)
            {
                System.out.print("\n\nHello Buyer\n\nWhat do you want to do?\n\n1. See all ads from today\n2. See all ads\n3. Log out\n4. Quit\n\n Choose an option (1-4): ");
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
                else if (op == 3)
                {
                    System.out.println("\nLogging out\n");
                    loggedBenutzer=null;
                }
                else if (op == 4)
                {
                    System.out.println("\nQuitting\n");
                    break;
                }
                else
                    System.out.print("Invalid selection. Please try again");
            }
            if(loggedBenutzer instanceof Seller)
            {
                System.out.print("\n\nHello Seller\n\nWhat do you want to do?\n\n1. Create an advert\n2. See your adverts\n3. See pending transactions\n4. Log out\n5. Quit\n\n Choose an option (1-5): ");
                Scanner myObj = new Scanner(System.in);
                int op = myObj.nextInt();
                if(op == 1)
                {
                    createAd();
                }
                else if (op == 2)
                {
                    System.out.println("\nYour adverts:\n");
                    presentAdsSeller(adsRepository.getAllAdsFromSeller((Seller) loggedBenutzer));
                }
                else if (op == 3)
                {
                    System.out.println("\nYour pending transactions:\n");
                    presentTransactions();
                }
                else if (op == 4)
                {
                    System.out.println("\nLogging out\n");
                    loggedBenutzer=null;
                }
                else if (op == 5)
                {
                    System.out.println("\nQuitting\n");
                    break;
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
        System.out.println("Car/Motorcycle (Press 1 or 0):");
        is_Car=Boolean.parseBoolean(myObj.nextLine());

        System.out.println("Make:");
        String make = myObj.nextLine();

        System.out.println("Model:");
        String model = myObj.nextLine();

        System.out.println("Year:");
        int year = Integer.parseInt(myObj.nextLine());

        System.out.println("Displacement:");
        int displacement = Integer.parseInt(myObj.nextLine());

        System.out.println("Horsepower:");
        int hp = Integer.parseInt(myObj.nextLine());

        System.out.println("Torque:");
        int torque = Integer.parseInt(myObj.nextLine());

        System.out.println("Used/new (Press 1 or 0):");
        boolean used= Boolean.parseBoolean(myObj.nextLine());

        System.out.println("Automatic or manual gearbox (Press 1 or 0):");
        boolean automaticGearbox= Boolean.parseBoolean(myObj.nextLine());

        System.out.println("Buy price:");
        int buyPrice = Integer.parseInt(myObj.nextLine());

        System.out.println("Auction start price (0 if you don't want an auction):");
        int startPrice = Integer.parseInt(myObj.nextLine());

        System.out.println("How many days would you like the Advert to stay active:");
        int auctionDays = Integer.parseInt(myObj.nextLine());

        if(is_Car){
            System.out.println("Number of doors:");
            int nrDoors=Integer.parseInt(myObj.nextLine());
            System.out.println("Number of seats:");
            int nrSeats=Integer.parseInt(myObj.nextLine());
            a = new Car((Seller) loggedBenutzer, auctionDays, make, model, year,displacement,hp,torque,used,automaticGearbox,nrDoors,nrSeats, buyPrice, startPrice);
        }
        else
        {
            System.out.println("Suspension type:");
            String suspensionType = myObj.nextLine();
            System.out.println("Brake type:");
            String brakeType = myObj.nextLine();
            a = new Motorcycle( (Seller) loggedBenutzer, auctionDays, make, model, year,displacement,hp,torque,used,automaticGearbox,suspensionType,brakeType, buyPrice, startPrice);
        }
        controller.sellCar(a);
    }

}
