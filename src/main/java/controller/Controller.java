package controller;

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
    public Controller(UserRepository userRepository, AdsRepository adsRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.transactionRepository = transactionRepository;
        //populate();
    }
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

        Advert a = new Car((Seller) userRepository.findId("veriku"), 0, "VW", "Taigo", 2022, 1499, 150, 200, false, false, 5, 4,23000, 0);
        Advert b = new Car((Seller) userRepository.findId("veriku"), 7, "VW", "Passat", 2012, 1999, 150, 200, false, false, 5, 4, 11000, 3000);
        Advert c = new Car((Seller) userRepository.findId("veriku"), 20, "Dacia", "Papuc", 2000, 1299, 150, 200, false, false, 5, 4, 4000, 800);
        adsRepository.add(a);
        adsRepository.add(b);
        adsRepository.add(c);

        Transaktion transaktion = new Transaktion((Buyer) userRepository.findId("iordache"), adsRepository.findId(2), 1020, true);
        Transaktion transaktion2 = new Transaktion((Buyer) userRepository.findId("andreigali"), adsRepository.findId(0), 10090, false);
        transactionRepository.add(transaktion);
        transactionRepository.add(transaktion2);
    }

    /**
     * returns the user with the given username or password
     * @param user the searched user
     * @param pass the searched password
     * @return the username with matching username and password
     * @throws //TODO
     */
    public Benutzer checkCreds(String user, String pass)

    {
        return userRepository.findByUserAnsPass(user, pass);
    }

    /**
     * gets the highest bid (in terms of price) on a given advert
     * @param advert the advert for which the highest bid is searched
     * @return the highest bid
     * @throws //TODO when there is no bid for the given car
     */
    public Transaktion getCurrentBid(Advert advert)
    {
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
        return transaktion;
    }

    /**
     * if a car has been bought, return the transaction through which this has been done
     * @param advert the advert which must have been bought
     * @return the transaction consisting of the purchase of the car
     * @throws //TODO when no one has bought the given car
     */
    public Transaktion getCurrentBuyer(Advert advert)
    {
        for (Transaktion t: transactionRepository.getTransactionsByCar(advert) )
        {
            if(!t.isBid())
            {
                return t;
            }
        }
        return null;
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
     * @throws //TODO de mai sus si daca are parametrii invalizi
     */
    public void sellCar(Advert e) // aka place advert
    {
        // business logic daca mai trebuie punem aici
        int year = Year.now().getValue();
        if(e.getYear() > 1980 & e.getYear() <= year+1) // car must be newer than 1980 and not from the future (1 year in the future is allowed e.g. 2023 Chevy Corvette C8 Z06)
            adsRepository.add(e);
    }

    /**
     * checks that a given bid is valid and adds it to the repository
     * @param t the bid that should be added
     * @throws //TODO de sus si daca bid ul e invalid
     */
    public void placeBid(Transaktion t)
    {
        long daysBetween = Duration.between(t.getAd().getPlaceDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays(); // how many days since ad posted
        if(adsRepository.findId(t.getAd().getID()) != null && !isExpired(t.getAd()))
            // the ad on which it is bid should still exist and the auction should still be ongoing (not more than auctionDays days should have elapsed)
            transactionRepository.add(t);
    }

    /**
     * checks that a given buy offer is valid and adds it to the repository
     * @param t the buy offer that should be added
     * @throws //TODO de sus si daca tranzactia e invalida
     */
    public void buyUpfront(Transaktion t)
    {
        if(adsRepository.findId(t.getAd().getID()) != null) // the ad on which it is bid should still exist
            transactionRepository.add(t);
    }

    /**
     * denies a buy offer/bid (action done by seller) aka it's removed from the repo
     * @param t the transaction that sould be denied
     * @throws //TODO de sus
     */
    public void denyTransaction(Transaktion t) // the seller doesn't accept the buy offer / the highest bid => the transaction gets deleted
    {
        transactionRepository.delete(t.getId());
    }

    /**
     * accepts a buy offer/bid (action done by seller) aka it's added to the repo
     * @param t the transaction that sould be accepted
     * @throws //TODO de sus
     */
    public void acceptTransaction(Transaktion t) // the seller accepts the buy offer / the highest bid => the transaction and ad get deleted
    {
        Buyer b = t.getBuyer();
        List<Transaktion> tr = transactionRepository.getTransactionsByCar(t.getAd());
        for (Transaktion transaktion: tr)
            transactionRepository.delete(transaktion.getId());
        adsRepository.delete(t.getAd().getID()); // car is sold => no longer available on the site

        b.setCarsBought(b.getCarsBought()+1);
        userRepository.update(b.getUsername(), b);
    }
}
