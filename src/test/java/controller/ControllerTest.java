package controller;

import exceptions.IllegalIdException;
import exceptions.InvalidCredsException;
import exceptions.InvalidInputException;
import exceptions.NoTransactionException;
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
    void checkCreds() {
        Buyer ioio = null;
        try {
            ioio = (Buyer) userRepository.findId("iordache");
        } catch (IllegalIdException e) {
            fail();
        }
        try {
            assertEquals(ioio, testedCtrl.checkCreds("iordache", "melissa"));
        } catch (InvalidCredsException e) {
            fail();
        }
        try {
            testedCtrl.checkCreds("iordache", "iulia"); // exception expected
            fail();
        } catch (InvalidCredsException e) {
            assertTrue(true);
        }
        try {
            testedCtrl.checkCreds("veriku", "melissa"); // exception expected
            fail();
        } catch (InvalidCredsException e) {
            assertTrue(true);
        }

    }

    @Test
    void getCurrentBid()
    {
        Transaktion t = null;
        try {
            t = transactionRepository.findId(0);
        } catch (IllegalIdException e) {
            fail();
        }
        Advert ad = null;
        try {
            ad = adsRepository.findId(2);
        } catch (IllegalIdException e) {
            fail();
        }
        Advert ad2 = null;
        try {
            ad2 = adsRepository.findId(1);
        } catch (IllegalIdException e) {
            fail();
        }
        try {
            assertEquals(t, testedCtrl.getCurrentBid(ad));
        } catch (NoTransactionException e) {
            fail();
        }
        try {
            testedCtrl.getCurrentBid(ad2); // exception expected
            fail();
        } catch (NoTransactionException e) {
            assertTrue(true);
        }
    }

    @Test
    void getCurrentBuyer()
    {
        Transaktion t = null;
        try {
            t = transactionRepository.findId(1);
        } catch (IllegalIdException e) {
            fail();
        }
        Advert ad = null;
        try {
            ad = adsRepository.findId(0);
        } catch (IllegalIdException e) {
            fail();
        }
        Advert ad2 = null;
        try {
            ad2 = adsRepository.findId(1);
        } catch (IllegalIdException e) {
            fail();
        }
        try {
            assertEquals(t, testedCtrl.getCurrentBuyer(ad));
        } catch (NoTransactionException e) {
            fail();
        }
        try {
            testedCtrl.getCurrentBuyer(ad2); // exception expected
            fail();
        } catch (NoTransactionException e) {
            assertTrue(true);
        }
    }

    @Test
    void getAuctionEndDate()
    {
        Advert ad = null;
        try {
            ad = adsRepository.findId(1);
        } catch (IllegalIdException e) {
            fail();
        }
        assertEquals(LocalDate.now().atStartOfDay().plusDays(7).toLocalDate(), testedCtrl.getAdvertExpDate(ad));
        Advert ad2 = null;
        try {
            ad2 = adsRepository.findId(2);
        } catch (IllegalIdException e) {
            fail();
        }
        assertEquals(LocalDate.now().atStartOfDay().plusDays(20).toLocalDate(), testedCtrl.getAdvertExpDate(ad2));
    }

    @Test
    void isElapsed()
    {
        Advert ad = null;
        try {
            ad = adsRepository.findId(1);
        } catch (IllegalIdException e) {
            fail();
        }
        assertFalse(testedCtrl.isExpired(ad));
        Advert ad2 = null;
        try {
            ad2 = adsRepository.findId(2);
        } catch (IllegalIdException e) {
            fail();
        }
        assertFalse(testedCtrl.isExpired(ad2));
    }

    @Test
    void isSold()
    {
        Advert ad = null;
        try {
            ad = adsRepository.findId(0);
        } catch (IllegalIdException e) {
            fail();
        }
        Advert ad2 = null;
        try {
            ad2 = adsRepository.findId(1);
        } catch (IllegalIdException e) {
            fail();
        }
        assertTrue(testedCtrl.isSold(ad));
        assertFalse(testedCtrl.isSold(ad2));
    }

    @Test
    void sellCar()
    {
        assertEquals(3, adsRepository.findAll().size());
        Advert c = null;
        try {
            c = new Car((Seller) userRepository.findId("veriku"), 20, "Dacia", "1300", 2000, 1299, 150, 200, false, false, 5, 4, 4000, 800);
        } catch (IllegalIdException e) {
            fail();
        }
        try {
            testedCtrl.sellCar(c);
        } catch (InvalidInputException e) {
            fail();
        }
        assertEquals(4, adsRepository.findAll().size());
    }

    @Test
    void placeBid()
    {
        assertEquals(2, transactionRepository.findAll().size());
        Transaktion transaktion = null;
        try {
            transaktion = new Transaktion((Buyer) userRepository.findId("iordache"), adsRepository.findId(2), 1720, true);
            testedCtrl.placeBid(transaktion);
        } catch (IllegalIdException | InvalidInputException e) {
            fail();
        }

        assertEquals(3, transactionRepository.findAll().size());
    }

    @Test
    void buyUpfront()
    {
        assertEquals(2, transactionRepository.findAll().size());
        Transaktion transaktion = null;
        try {
            transaktion = new Transaktion((Buyer) userRepository.findId("iordache"), adsRepository.findId(2), 1720, false);
            testedCtrl.buyUpfront(transaktion);
        } catch (IllegalIdException | InvalidInputException e) {
            fail();
        }

        assertEquals(3, transactionRepository.findAll().size());
    }

    @Test
    void denyTransaction()
    {
        assertEquals(2, transactionRepository.findAll().size());
        Transaktion t = null;
        try {
            t = transactionRepository.findId(0);
            testedCtrl.denyTransaction(t);
        } catch (IllegalIdException | NoTransactionException e) {
            fail();
        }

        assertEquals(1, transactionRepository.findAll().size());
    }

    @Test
    void acceptTransaction()
    {
        assertEquals(2, transactionRepository.findAll().size());
        assertEquals(3, adsRepository.findAll().size());
        Transaktion t = null;
        try {
            t = transactionRepository.findId(0);
            testedCtrl.acceptTransaction(t);
        } catch (IllegalIdException | NoTransactionException e) {
            fail();
        }
        assertEquals(1, transactionRepository.findAll().size());
        assertEquals(2, adsRepository.findAll().size());
    }
}