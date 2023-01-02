package controller;

import exceptions.IllegalIdException;
import exceptions.InvalidCredsException;
import exceptions.InvalidInputException;
import exceptions.NoTransactionException;
import model.*;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public class Controller
{
    private UserRepository userRepository;
    private AdsRepository adsRepository;
    private TransactionRepository transactionRepository;
    public Controller(UserRepository userRepository, AdsRepository adsRepository, TransactionRepository transactionRepository, boolean pop) {
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.transactionRepository = transactionRepository;
        if(pop)
            populate();
    }

    private void populate()
    {
        userRepository.add(new Buyer("andreigali","42069","Cristian, BV"));
        userRepository.add(new Buyer("iordache","melissa","Brasov, BV"));
        userRepository.add(new Seller("veriku","iazivericule","Pitesti, AG"));
        userRepository.add(new Admin("vincenzo","gen","pe Italia"));

        Advert a = null;
        Advert b = null;
        Advert c = null;
        try {
            a = new Car((Seller) userRepository.findId("veriku"), 0, "VW", "Taigo", 2022, 1499, 150, 200, false, false, 5, 4,23000, 0);
            b = new Car((Seller) userRepository.findId("veriku"), 7, "VW", "Passat", 2012, 1999, 150, 200, false, false, 5, 4, 11000, 3000);
            c = new Car((Seller) userRepository.findId("veriku"), 20, "Dacia", "Papuc", 2000, 1299, 150, 200, false, false, 5, 4, 4000, 800);
        } catch (IllegalIdException e) {
            throw new RuntimeException(e); // should never happen as everything is hard-coded
        }

        adsRepository.add(a);
        adsRepository.add(b);
        adsRepository.add(c);

        Transaktion transaktion = null;
        Transaktion transaktion2 = null;
        try {
            transaktion = new Transaktion((Buyer) userRepository.findId("iordache"), adsRepository.findId(2), 1020, true);
            transaktion2 = new Transaktion((Buyer) userRepository.findId("andreigali"), adsRepository.findId(0), 10090, false);

        } catch (IllegalIdException e) {
            throw new RuntimeException(e); // should never happen as everything is hard-coded
        }
        transactionRepository.add(transaktion);
        transactionRepository.add(transaktion2);
    }

    /**
     * returns the user with the given username or password
     * @param user the searched user
     * @param pass the searched password
     * @return the username with matching username and password
     * @throws InvalidCredsException when the credentials are invalid for any reason
     */
    public Benutzer checkCreds(String user, String pass) throws InvalidCredsException {
        Benutzer b = userRepository.findByUserAnsPass(user, pass);
        if(b!=null)
            return b;
        else
            throw new InvalidCredsException();
    }

    /**
     * gets the highest bid (in terms of price) on a given advert
     * @param advert the advert for which the highest bid is searched
     * @return the highest bid
     * @throws NoTransactionException when there is no bid available
     */
    public Transaktion getCurrentBid(Advert advert) throws NoTransactionException {
        int currentAmount = advert.getStartPrice();
        Transaktion transaktion = null;
        for (Transaktion t: transactionRepository.getTransactionsByCar(advert) )
        {
            if(t.isBid() && t.getAmount() > currentAmount)
            {
                currentAmount = t.getAmount();
                transaktion = t;
            }

        }
        if(transaktion == null)
            throw new NoTransactionException();
        return transaktion;
    }

    /**
     * if a car has been bought, return the transaction through which this has been done
     * @param advert the advert which must have been bought
     * @return the transaction consisting of the purchase of the car
     * @throws NoTransactionException when no one has bought the car
     */
    public Transaktion getCurrentBuyer(Advert advert) throws NoTransactionException {
        for (Transaktion t: transactionRepository.getTransactionsByCar(advert) )
        {
            if(!t.isBid())
            {
                return t;
            }
        }
        throw new NoTransactionException();
    }

    /**
     * gets the date at which an advert will expire
     * @param advert the advert to calculate the expiry date on
     * @return the date of expiry for given advert
     */
    public LocalDate getAdvertExpDate(Advert advert)
    {
        return advert.getPlaceDate().plusDays(advert.getAuctionDays());
    }

    /**
     * checks whether an advert has expired or not
     * @param a the ad that should be checked
     * @return true if expired; false if not
     */
    public boolean isExpired(Advert a)
    {
        long daysBetween = Duration.between(a.getPlaceDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays(); // how many days since ad posted
        return daysBetween >= a.getAuctionDays();
    }

    /**
     * check whether an advert has been sold
     * @param a the ad that should be checked
     * @return true if sold; false if not
     */
    public boolean isSold(Advert a)
    {
        List<Transaktion> list = transactionRepository.getTransactionsByCar(a);
        list = list.stream().filter(n -> !n.isBid()).toList();
        return list.size() >= 1;
    }

    /**
     * places an advert in the repository and checks if it's parameters are valid
     * @param e the advert that should be added
     * @throws InvalidInputException if the attributes of the car are not valid
     */
    public void sellCar(Advert e) throws InvalidInputException // aka place advert
    {
        // business logic daca mai trebuie punem aici
        int year = Year.now().getValue();
        if(e.getYear() > 1980 & e.getYear() <= year+1) // car must be newer than 1980 and not from the future (1 year in the future is allowed e.g. 2023 Chevy Corvette C8 Z06)
            adsRepository.add(e);
        else
            throw new InvalidInputException();
    }

    /**
     * checks that a given bid is valid and adds it to the repository
     * @param t the bid that should be added
     * @throws InvalidInputException if the bid is invalid
     */
    public void placeBid(Transaktion t) throws InvalidInputException {
        Advert a = null;
        try {
            a = adsRepository.findId(t.getAd().getID());
        } catch (IllegalIdException e) {
            throw new InvalidInputException(); // the ad has been removed from the repo in the meantime => transaction is not valid
        }
        if(a != null && !isExpired(t.getAd()))
            // the ad on which it is bid should still exist and the auction should still be ongoing (not more than auctionDays days should have elapsed)
            transactionRepository.add(t);
        else
            throw new InvalidInputException();
    }

    /**
     * checks that a given buy offer is valid and adds it to the repository
     * @param t the buy offer that should be added
     * @throws InvalidInputException if the buy offer is invalid
     */
    public void buyUpfront(Transaktion t) throws InvalidInputException {

        try {
            adsRepository.findId(t.getAd().getID());
        } catch (IllegalIdException e) {
            throw new InvalidInputException(); // the ad has been removed from the repo in the meantime => transaction is not valid
        }

        transactionRepository.add(t);
    }

    /**
     * denies a buy offer/bid (action done by seller) aka it's removed from the repo
     * @param t the transaction that sould be denied
     * @throws NoTransactionException if the transaction is invalid
     */
    public void denyTransaction(Transaktion t) throws NoTransactionException // the seller doesn't accept the buy offer / the highest bid => the transaction gets deleted
    {
        try {
            transactionRepository.delete(t.getId());
        } catch (IllegalIdException e) {
            throw new NoTransactionException(); // the transaction has been deleted in the meantime
        }
    }

    /**
     * accepts a buy offer/bid (action done by seller) aka it's added to the repo
     * @param t the transaction that sould be accepted
     * @throws NoTransactionException if the transaction is invalid
     */
    public void acceptTransaction(Transaktion t) throws NoTransactionException // the seller accepts the buy offer / the highest bid => the transaction and ad get deleted
    {
        Buyer b = t.getBuyer();
        List<Transaktion> tr = transactionRepository.getTransactionsByCar(t.getAd());
        try {
            for (Transaktion transaktion : tr)
                transactionRepository.delete(transaktion.getId());
            adsRepository.delete(t.getAd().getID()); // car is sold => no longer available on the site
            b.setCarsBought(b.getCarsBought()+1);
            userRepository.update(b.getUsername(), b);
        }
        catch (IllegalIdException e)
        {
            throw new NoTransactionException();
        }
    }
}
