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
    }
    public int checkCreds(String user, String pass)
    {
        User u = userRepository.findByUserAnsPass(user, pass);
        if(u instanceof Buyer)
            return 1;
        if(u instanceof Seller)
            return 2;
        if(u instanceof Admin)
            return 0;
        return -1;
    }

    // TODO check for elapsed auctions and confront the seller with the final offer, should there be one
    // TODO check for ads older than one month and confront the seller with the final offer, should there be one

    void sellCar(Advert e) // aka place advert
    {
        // business logic daca mai trebuie punem aici
        int year = Year.now().getValue();
        if(e.getYear() > 1980 & e.getYear() <= year+1) // car must be newer than 1980 and not from the future (1 year in the future is allowed e.g. 2023 Chevy Corvette C8 Z06)
            adsRepository.add(e);
    }

    void placeBid(Transaction t)
    {
        long daysBetween = Duration.between(t.getAd().getPlaceDate(), LocalDate.now()).toDays(); // how many days since ad posted
        if(adsRepository.findId(t.getAd().getBuyPrice()) != null && daysBetween <= t.getAd().getAuctionDays())
            // the ad on which it is bid should still exist and the auction should still be ongoing (not more than auctionDays days should have elapsed)
            transactionRepository.add(t);
    }

    void buyUpfront(Transaction t)
    {
        if(adsRepository.findId(t.getAd().getBuyPrice()) != null) // the ad on which it is bid should still exist
            transactionRepository.add(t);
    }

    void denyTransaction(Transaction t) // the seller doesn't accept the buy offer / the highest bid => the transaction gets deleted
    {
        transactionRepository.delete(t.getId());
    }

    void acceptTransaction(Transaction t) // the seller accepts the buy offer / the highest bid => the transaction and ad get deleted
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
