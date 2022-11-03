package controller;

import model.*;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import repository.memory_repo.InMemoryCarRepository;
import repository.memory_repo.InMemoryTransactionRepository;
import repository.memory_repo.InMemoryUserRepository;

import java.util.ArrayList;
import java.util.List;

public class Controller
{
    private UserRepository userRepository;
    private AdsRepository adsRepository;

    private TransactionRepository transactionRepository;

    // toate functiile userilor aici; in view facem trierea dupa tipul de user
    public Controller() {
        this.userRepository = new InMemoryUserRepository();
        this.adsRepository = new InMemoryCarRepository();
        this.transactionRepository = new InMemoryTransactionRepository();
    }

    // toate functiile userilor aici; in view facem trierea dupa felul de user

    public int checkCreds(String user, String pass)
    {
        User u = userRepository.findByUserAnsPass(user, pass);
        if(u instanceof Buyer)
            return 1;
        if(u instanceof Seller)
            return 2;
        return 0;
    }

    void sellCar(Advert e) // aka place advert
    {
        // business logic daca mai trebuie punem aici
        adsRepository.add(e);
    }

    void placeBid(Transaction t)
    {
        //TODO de verificat sa nu fi trecut termenul licitatiei inainte de a plasa bid-ul
        if(adsRepository.findId(t.getAd().getBuyPrice()) != null) // the ad on which it is bid should still exist
            transactionRepository.add(t);

    }

    List<Advert> getAllAdsFromSeller(Seller s)
    {
        List<Advert> allAds = adsRepository.findAll();
        List<Advert> result = new ArrayList<>();
        for (Advert ad : allAds)
        {
            if (ad.getSeller() == s)
                result.add(ad);
        }
        return result;
    }

    void buyUpfront(Transaction t)
    {
        if(adsRepository.findId(t.getAd().getBuyPrice()) != null) // the ad on which it is bid should still exist
            transactionRepository.add(t);
    }

    List<Advert> getAllAds()
    {
        return adsRepository.findAll();
    }

    Advert getAdByID(int id)
    {
        return adsRepository.findId(id);
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
