package controller;

import model.*;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Year;

public class Controller
{
    private UserRepository userRepository;
    private AdsRepository adsRepository;
    private TransactionRepository transactionRepository;
    public Controller(UserRepository userRepository, AdsRepository adsRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.transactionRepository = transactionRepository;
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

        Transaction transaction = new Transaction((Buyer) userRepository.findId("iordache"), adsRepository.findId(2), 1020, true);
        transactionRepository.add(transaction);
    }

    public User checkCreds(String user, String pass)
    {
        User u = userRepository.findByUserAnsPass(user, pass);
        return u;
    }

    public Transaction getCurrentBid(Advert advert)
    {
        int currentAmount = advert.getStartPrice();
        Transaction transaction = null;
        for (Transaction t: transactionRepository.getTransactionsByCar(advert) )
        {
            if(t.isBid() && t.getAmount() > currentAmount)
            {
                currentAmount = t.getAmount();
                transaction = t;
            }

        }
        return transaction;
    }

    public LocalDate getAuctionEndDate(Advert advert)
    {
        return advert.getPlaceDate().plusDays(advert.getAuctionDays());
    }

    // TODO check for elapsed auctions and confront the seller with the final offer, should there be one
    // TODO check for ads older than one month and confront the seller with the final offer, should there be one

    public void sellCar(Advert e) // aka place advert
    {
        // business logic daca mai trebuie punem aici
        int year = Year.now().getValue();
        if(e.getYear() > 1980 & e.getYear() <= year+1) // car must be newer than 1980 and not from the future (1 year in the future is allowed e.g. 2023 Chevy Corvette C8 Z06)
            adsRepository.add(e);
    }

    public void placeBid(Transaction t)
    {
        long daysBetween = Duration.between(t.getAd().getPlaceDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays(); // how many days since ad posted
        if(adsRepository.findId(t.getAd().getID()) != null && daysBetween <= t.getAd().getAuctionDays())
            // the ad on which it is bid should still exist and the auction should still be ongoing (not more than auctionDays days should have elapsed)
            transactionRepository.add(t);
    }

    public void buyUpfront(Transaction t)
    {
        if(adsRepository.findId(t.getAd().getBuyPrice()) != null) // the ad on which it is bid should still exist
            transactionRepository.add(t);
    }

    public void denyTransaction(Transaction t) // the seller doesn't accept the buy offer / the highest bid => the transaction gets deleted
    {
        transactionRepository.delete(t.getId());
    }

    public void acceptTransaction(Transaction t) // the seller accepts the buy offer / the highest bid => the transaction and ad get deleted
    {
        adsRepository.delete(t.getAd().getID()); // car is sold => no longer available on the site
        transactionRepository.delete(t.getId()); // car no longer available => transaction references non-existent car and is deleted
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AdsRepository getAdsRepository() {
        return adsRepository;
    }

    public void setAdsRepository(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
