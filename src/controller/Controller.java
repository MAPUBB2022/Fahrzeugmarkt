package controller;

import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;

public class Controller
{
    private UserRepository userRepository;
    private AdsRepository adsRepository;

    private TransactionRepository transactionRepository;

    // toate functiile userilor aici; in view facem trierea dupa felul de user

    int checkCreds(String user, String pass)
    {
        return 0;
    }

    void sellCar()
    {

    }

    void placeBid()
    {

    }

    void buyUpfront()
    {

    }



    public Controller(UserRepository userRepository, AdsRepository adsRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
        this.transactionRepository = transactionRepository;
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
