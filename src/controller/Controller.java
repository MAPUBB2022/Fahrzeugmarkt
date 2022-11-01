package controller;

import model.Admin;
import model.Buyer;
import model.Seller;
import model.User;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import repository.memory_repo.InMemoryCarRepository;
import repository.memory_repo.InMemoryTransactionRepository;
import repository.memory_repo.InMemoryUserRepository;

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

    // craca lu atletu

    void sellCar()
    {


    }

    void placeBid()
    {

    }

    void buyUpfront()
    {

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
