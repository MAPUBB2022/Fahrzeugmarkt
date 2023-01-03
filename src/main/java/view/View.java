package view;

import controller.Controller;
import exceptions.IllegalIdException;
import exceptions.InvalidCredsException;
import exceptions.InvalidInputException;
import exceptions.NoTransactionException;
import model.*;
import repository.AdsRepository;
import repository.UserRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class View
{
    private final Controller controller;
    private final UserRepository userRepository;
    private final AdsRepository adsRepository;

    private Benutzer loggedBenutzer;

    public View(Controller controller, UserRepository userRepository, AdsRepository adsRepository) {
        this.controller = controller;
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        //////////////////////////////
        this.loggedBenutzer = null;
    }

    /**
     * reads an integer from the user and returns it
     * @return the integer retrieved from the user
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                // Print an error message and ask the user to enter a valid integer
                System.out.println("Invalid input. Please enter a valid integer.");
                // Clear the scanner's buffer to discard the invalid input
                scanner.nextLine();
            }
        }
    }

    /**
     * reads a boolean from the user and returns it
     * @return the boolean value retrieved from the user
     */
    private boolean readBoolean() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                return scanner.nextBoolean();
            } catch (InputMismatchException e) {
                // Print an error message and ask the user to enter a valid boolean value
                System.out.println("Invalid input. Please enter a valid boolean value (true or false).");
                // Clear the scanner's buffer to discard the invalid input
                scanner.nextLine();
            }
        }
    }


    /**
     * prints all the details of an advert to the screen
     * @param advert the advert that should be detailed
     */
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

    /**
     * prints a summary of a given advert
     * @param advert the advert that should be summarized
     */
    private void printAdSummary(Advert advert)
    {
        if(advert instanceof Car)
            System.out.print("Car\t");

        if(advert instanceof Motorcycle)
            System.out.print("Motorcycle\t");

        System.out.printf("%s %s %s; %s Euro\n", advert.getMake(), advert.getModel(), advert.getYear(),advert.getBuyPrice());
    }

    /**
     * prints the details of a transaction on the screen
     * @param transaktion the transaction that should be detailed
     */
    private void printTransaction(Transaktion transaktion)
    {
        if(transaktion.isBid())
            System.out.printf("\t%s, %s: bid %s Euro\n", transaktion.getBuyer().getUsername(), transaktion.getDate(), transaktion.getAmount());
        else
            System.out.printf("\t%s, %s: bought with %s Euro\n", transaktion.getBuyer().getUsername(), transaktion.getDate(), transaktion.getAmount());
    }

    /**
     * user interface for logging in
     * asks user for username and password
     */
    public void login()
    {
        try
        {
                Scanner myObj = new Scanner(System.in);
                System.out.println("Enter username: ");
                String username = myObj.nextLine();

                System.out.println("Enter password: ");
                String password = myObj.nextLine();

                loggedBenutzer = controller.checkCreds(username, password);
        }
        catch (InvalidCredsException e) {
            System.out.println("Login failed! Try again");
        }
    }

    /**
     * user interface for interacting with adverts (for buyers)
     * for any ad the user can see its details, buy it or bid on it
     * @param adList the adverts that should be presented to the user
     */
    private void presentAdsBuyer(List<Advert> adList)
    {
        Scanner inputScanner = new Scanner(System.in);  // Create a Scanner object
        adList = adList.stream().filter(n -> !controller.isExpired(n) && !controller.isSold(n)).collect(Collectors.toList());
        int globalCounter=0;
        int i;
        while(globalCounter<adList.size())
        {
            for(i=0; i<10 && i+globalCounter < adList.size(); i++) // "Page size" is 10
            {
                System.out.printf("%d\t", i+1);
                printAdSummary(adList.get(globalCounter+i));
            }
            System.out.printf("Select a car (1-%s) or press enter to see the next page: ",i);

            String userInput = inputScanner.nextLine();
            int inputToInt;
            try {
                inputToInt=Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                globalCounter+=i;
                continue;
            }

            if(inputToInt>=1 && inputToInt<=10)
            {
                //present ad in a detailed fashion
                Advert currentAd = adList.get(globalCounter+inputToInt-1);
                printAd(currentAd);
                Transaktion currentBid;
                try {
                    currentBid = controller.getCurrentBid(currentAd);
                    System.out.printf("\nThe current bid on this Advert is %s Euro\n", currentBid.getAmount());
                } catch (NoTransactionException e) {
                    System.out.print("\nThere are no bids on this Advert\n");
                }

                System.out.println("What would you like to do: \n1. Place a bid\n2. Buy the car\n\nChoose an option (1-2): ");
                int opt = readInt();
                if(opt==1)
                {
                    System.out.println("How much would you like to bid? ");
                    int amount = readInt();
                    Transaktion transaktion = new Transaktion((Buyer) loggedBenutzer, currentAd, amount, true);
                    try {
                        controller.placeBid(transaktion);
                    } catch (InvalidInputException e) {
                        System.out.println("Bid has failed.");
                    }
                }
                else if (opt==2)
                {
                    Transaktion transaktion = new Transaktion((Buyer) loggedBenutzer, currentAd, currentAd.getBuyPrice(), false);
                    try {
                        controller.buyUpfront(transaktion);
                        System.out.println("Transaction completed successfully!");
                    } catch (InvalidInputException e) {
                        System.out.println("Transaction failed.");
                    }

                }
                break;
            }
        }
    }

    /**
     * user interface for seeing adverts (for sellers)
     * the seller can see vital information for all his adverts
     * @param adList the adverts that should be presented to the user
     */
    private void presentAdsSeller(List<Advert> adList)
    {
        for (Advert a: adList )
        {
            if(controller.isExpired(a))
                System.out.println("\nThis Advert has expired!");
            if(controller.isSold(a))
                System.out.println("\nThis Advert has been sold!");
            printAdSummary(a);
            Transaktion currentBid;
            try {
                currentBid = controller.getCurrentBid(a);
                System.out.printf("\tCurrent bid: %s\n\n", currentBid.getAmount());
            } catch (NoTransactionException e) {
                System.out.print("\tThere is currently no bid\n\n");
            }
        }
    }

    /**
     * user interface for interacting between the actions of buyers and sellers.
     * sellers can see what transactions buyers have proposed and accept or deny them
     * if an ad expires without being bought, it is also shown here
     */
    private void presentTransactions()
    {
        List<Advert> adverts = adsRepository.getAllAdsFromSeller((Seller) loggedBenutzer);
        adverts = adverts.stream().filter(n -> controller.isExpired(n) || controller.isSold(n) ).collect(Collectors.toList());

        int globalCounter=0;
        int i=0;
        while(globalCounter<adverts.size())
        {
            for(i=0; i<10 && i+globalCounter < adverts.size(); i++) // "Page size" is 10
            {
                System.out.printf("%d\t", i+1);
                printAdSummary(adverts.get(globalCounter+i));
                if (adverts.get(globalCounter+i).getAuctionDays() > 0)
                {
                    if (!controller.isExpired(adverts.get(globalCounter+i)))
                        System.out.printf("\tAuction will end on %s\n", controller.getAdvertExpDate(adverts.get(globalCounter+i)));
                    else
                        System.out.print("\tAuction has ended!\n");
                }
                else
                    System.out.print("\tThis is not an auction\n");

                try {
                    Transaktion transaktion = controller.getCurrentBid(adverts.get(globalCounter+i));
                    printTransaction(transaktion);
                } catch (NoTransactionException e) {
                    System.out.println("\tNo bids on this advert.\n");
                }
            }
            System.out.printf("Select a car (1-%s) or press enter to see the next page: ",i);
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            String userInput = myObj.nextLine();
            int inputToInt;
            try
            {
                inputToInt = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                globalCounter+=i;
                continue;
            }


            if(inputToInt>=1 && inputToInt<=10)
            {
                Advert currentAd = adverts.get(globalCounter+inputToInt-1);
                if(controller.isSold(currentAd))
                {
                    try {
                        Transaktion t = controller.getCurrentBuyer(currentAd);
                        System.out.printf("%s has bought this advert!\n", t.getBuyer().getUsername());
                        System.out.println("What would you like to do: \n1. Accept the transaction\n2. Deny the transaction\n\nChoose an option (1-2): ");
                        int opt = readInt();
                        if(opt==1)
                        {
                            controller.acceptTransaction(t);
                        }
                        else if (opt==2)
                        {
                            controller.denyTransaction(t);
                        }
                    } catch (NoTransactionException e) {
                        System.out.println("The transaction has been retracted");
                    }
                }
                else if(controller.isExpired(currentAd))
                {
                    try {
                        Transaktion currentTransaction = controller.getCurrentBid(currentAd);
                        System.out.printf("%s has won this advert!\n", currentTransaction.getBuyer().getUsername());
                        System.out.println("What would you like to do: \n1. Accept the transaction\n2. Deny the transaction\n\nChoose an option (1-2): ");
                        int opt = readInt();
                        if(opt==1)
                        {
                            controller.acceptTransaction(currentTransaction);
                        }
                        else if (opt==2)
                        {
                            controller.denyTransaction(currentTransaction);
                        }
                    } catch (NoTransactionException e) {
                        System.out.print("The Advert has expired, and there are no bids or buy offers.\n");
                        System.out.println("What would you like to do: \n1. Remove the advert\n2. Refresh the advert\n\nChoose an option (1-2): ");
                        int opt = readInt();
                        if(opt==1)
                        {
                            try {
                                adsRepository.delete(currentAd.getID());
                            } catch (IllegalIdException ex) {
                                System.out.println("Delete failed");
                            }
                        }
                        else if (opt==2)
                        {
                            System.out.println("For how many days would you like the Advert to be active: ");
                            int days = readInt();
                            currentAd.setAuctionDays(days);
                            try {
                                adsRepository.update(currentAd.getID(), currentAd);
                            } catch (IllegalIdException ex) {
                                System.out.println("Update failed");
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * interface for removing users from the platform (designed for admins)
     * the admin is asked for the username of the user selected for deletion
     */
    public void removeUser()
    {
        System.out.print("Which user would you like to remove: ");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userInput = myObj.nextLine();
        try {
            userRepository.delete(userInput);
        } catch (IllegalIdException e) {
            System.out.println("User doesn't exist!");
        }
    }

    /**
     * interface for adding users to the platform
     * user is prompted for username, password and location
     */
    public void addUser()
    {
        System.out.print("Enter a username: ");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userInput = myObj.nextLine();

        try
        {
            userRepository.findId(userInput);
            System.out.println("Username is taken\n");
        }
        catch (IllegalIdException e)
        {
            System.out.println("Enter a password: ");
            String pass = myObj.nextLine();
            System.out.println("Enter your location: ");
            String location = myObj.nextLine();
            System.out.print("What kind of user is this?\n0 - Admin\n1 - Buyer\n2 - Seller\nChoose a type (1-3): ");
            boolean success = false;
            Benutzer b = null;
            while(!success)
            {
                success = true;
                int type = readInt();
                if(type == 0)
                    b = new Admin(userInput, pass, location);
                else if(type==1)
                    b = new Buyer(userInput, pass, location);
                else if(type==2)
                    b = new Seller(userInput, pass, location);
                else
                    success = false;
            }
            userRepository.add(b);
        }
    }

    /**
     * the main menu of the console application
     * user logs in, then the appropriate functionality for each user type is available
     */
    public void mainMenu()
    {
        while (true)
        {
            if (loggedBenutzer == null)
            {
                login();
                if(loggedBenutzer != null)
                    continue;
            }

            if(loggedBenutzer instanceof Admin)
            {
                System.out.print("\n\nHello Admin\n\nWhat do you want to do?\n\n1. Add user\n2. Remove user\n3. Log out\n4. Quit\n\n Choose an option (1-4): ");
                int op = readInt();
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
                int op = readInt();
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
                int op = readInt();
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

    /**
     * interface for creating an advert
     * the user is prompted for all parameters in oder to add the car to the platform
     */
    public void createAd(){
        boolean is_Car;
        Advert a;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Car/Motorcycle (Type true or false):");
        is_Car=readBoolean();

        System.out.println("Make:");
        String make = myObj.nextLine();

        System.out.println("Model:");
        String model = myObj.nextLine();

        System.out.println("Year:");
        int year =  readInt();

        System.out.println("Displacement:");
        int displacement = readInt();

        System.out.println("Horsepower:");
        int hp = readInt();

        System.out.println("Torque:");
        int torque = readInt();

        System.out.println("Used/new (Type true or false):");
        boolean used= readBoolean();

        System.out.println("Automatic or manual gearbox (Type true or false):");
        boolean automaticGearbox= readBoolean();

        System.out.println("Buy price:");
        int buyPrice = readInt();

        System.out.println("Auction start price (0 if you don't want an auction):");
        int startPrice = readInt();

        System.out.println("How many days would you like the Advert to stay active:");
        int auctionDays = readInt();

        if(is_Car){
            System.out.println("Number of doors:");
            int nrDoors=readInt();
            System.out.println("Number of seats:");
            int nrSeats=readInt();
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
        try {
            controller.sellCar(a);
        } catch (InvalidInputException e) {
            System.out.println("Adding the car failed. Invalid parameters");
        }
    }

}
