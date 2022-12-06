package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import repository.memory_repo.InMemoryCarRepository;
import repository.memory_repo.InMemoryTransactionRepository;
import repository.memory_repo.InMemoryUserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    AdsRepository adsRepository;
    UserRepository userRepository;
    TransactionRepository transactionRepository;

    Controller testedCtrl;

    @BeforeEach
    void setUp()
    {
        adsRepository = new InMemoryCarRepository();
        userRepository = new InMemoryUserRepository();
        transactionRepository = new InMemoryTransactionRepository();
        testedCtrl = new Controller(userRepository, adsRepository, transactionRepository, true);
    }

    @Test
    void checkCreds()
    {
        Buyer ioio = (Buyer) userRepository.findId("iordache");
        assertEquals(ioio, testedCtrl.checkCreds("iordache", "melissa"));
        assertNull(testedCtrl.checkCreds("iordache", "iulia"));
        assertNull(testedCtrl.checkCreds("veriku", "melissa"));
    }

    @Test
    void getCurrentBid()
    {
        Transaktion t = transactionRepository.findId(0);
        Advert ad = adsRepository.findId(2);
        Advert ad2 = adsRepository.findId(1);
        assertEquals(t, testedCtrl.getCurrentBid(ad));
        assertNull(testedCtrl.getCurrentBid(ad2));
    }

    @Test
    void getCurrentBuyer()
    {
        Transaktion t = transactionRepository.findId(1);
        Advert ad = adsRepository.findId(0);
        Advert ad2 = adsRepository.findId(1);
        assertEquals(t, testedCtrl.getCurrentBuyer(ad));
        assertNull(testedCtrl.getCurrentBuyer(ad2));
    }

    @Test
    void getAuctionEndDate()
    {
        Advert ad = adsRepository.findId(1);
        assertEquals(LocalDate.now().atStartOfDay().plusDays(7).toLocalDate(), testedCtrl.getAuctionEndDate(ad));
        Advert ad2 = adsRepository.findId(2);
        assertEquals(LocalDate.now().atStartOfDay().plusDays(20).toLocalDate(), testedCtrl.getAuctionEndDate(ad2));
    }

    @Test
    void isElapsed()
    {
        Advert ad = adsRepository.findId(1);
        assertFalse(testedCtrl.isElapsed(ad));
        Advert ad2 = adsRepository.findId(2);
        assertFalse(testedCtrl.isElapsed(ad2));
    }

    @Test
    void isSold()
    {
        Advert ad = adsRepository.findId(0);
        Advert ad2 = adsRepository.findId(1);
        assertTrue(testedCtrl.isSold(ad));
        assertFalse(testedCtrl.isSold(ad2));
    }

    @Test
    void sellCar()
    {
        assertEquals(3, adsRepository.findAll().size());
        Advert c = new Car((Seller) userRepository.findId("veriku"), 20, "Dacia", "1300", 2000, 1299, 150, 200, false, false, 5, 4, 4000, 800);
        testedCtrl.sellCar(c);
        assertEquals(4, adsRepository.findAll().size());
    }

    @Test
    void placeBid()
    {
        assertEquals(2, transactionRepository.findAll().size());
        Transaktion transaktion = new Transaktion((Buyer) userRepository.findId("iordache"), adsRepository.findId(2), 1720, true);
        testedCtrl.placeBid(transaktion);
        assertEquals(3, transactionRepository.findAll().size());
    }

    @Test
    void buyUpfront()
    {
        assertEquals(2, transactionRepository.findAll().size());
        Transaktion transaktion = new Transaktion((Buyer) userRepository.findId("iordache"), adsRepository.findId(2), 1720, false);
        testedCtrl.buyUpfront(transaktion);
        assertEquals(3, transactionRepository.findAll().size());
    }

    @Test
    void denyTransaction()
    {
        assertEquals(2, transactionRepository.findAll().size());
        Transaktion t = transactionRepository.findId(0);
        testedCtrl.denyTransaction(t);
        assertEquals(1, transactionRepository.findAll().size());
    }

    @Test
    void acceptTransaction()
    {
        assertEquals(2, transactionRepository.findAll().size());
        assertEquals(3, adsRepository.findAll().size());
        Transaktion t = transactionRepository.findId(0);
        testedCtrl.acceptTransaction(t);
        assertEquals(1, transactionRepository.findAll().size());
        assertEquals(2, adsRepository.findAll().size());
    }
}