package repository.memory_repo;

import model.Advert;
import model.Car;
import model.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.ZoneId;

import static java.time.Instant.ofEpochMilli;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCarRepositoryTest {
    InMemoryCarRepository testedRepo;
    @BeforeEach
    void setUp()
    {
        testedRepo = new InMemoryCarRepository(true);
    }

    @Test
    void getAllAdsFromSeller()
    {
        assertTrue(testedRepo.getAllAdsFromSeller(null).isEmpty());
        Seller s = new Seller("veriku", "1234", "Pitesti");
        assertEquals(2, testedRepo.getAllAdsFromSeller(s).size());
        Seller bogusSeller = new Seller("ionutz", "parola0", "brasov");
        assertTrue(testedRepo.getAllAdsFromSeller(bogusSeller).isEmpty());
    }

    @Test
    void getAllAdsFromToday()
    {
        assertEquals(3, testedRepo.getAllAdsFromToday().size());
        Clock constantClock = Clock.fixed(ofEpochMilli(0), ZoneId.systemDefault()); // setting time to 1970
        assertTrue(testedRepo.getAllAdsFromToday(constantClock).isEmpty());
    }

    @Test
    void add()
    {
        Seller newSeller = new Seller("Klaus", "AmUnCreierAMG", "Bucale");
        Advert newAd = new Car(newSeller, 3, "BMW", "X3", 2022, 1995, 204, 230, false, true, 5, 5, 45000, 30000 );
        testedRepo.add(newAd);
        assertEquals(4, testedRepo.findAll().size());
        assertEquals(1, testedRepo.getAllAdsFromSeller(newSeller).size());
        assertEquals("BMW", testedRepo.getAllAdsFromSeller(newSeller).get(0).getMake());
    }

    @Test
    void delete()
    {
        Seller s = new Seller("veriku", "1234", "Pitesti");

        testedRepo.delete(0);
        assertEquals(1, testedRepo.getAllAdsFromSeller(s).size());
        testedRepo.delete(1);
        assertEquals(0, testedRepo.getAllAdsFromSeller(s).size());
        testedRepo.delete(0);
        testedRepo.delete(0);
        testedRepo.delete(0);
        assertEquals(0, testedRepo.getAllAdsFromSeller(s).size());
    }

    @Test
    void update()
    {
        assertEquals("Taigo", testedRepo.findAll().get(0).getModel());
        Advert ad = testedRepo.findAll().get(0);
        ad.setModel("T-Cross");
        testedRepo.update(0, ad);
        assertEquals("T-Cross", testedRepo.findAll().get(0).getModel());
        ad.setModel("Touareg");
        testedRepo.update(1, ad);
        assertEquals("Touareg", testedRepo.findAll().get(1).getModel());

        testedRepo.update(42069, ad); // invalid id-s are ignored and nothing is changed
        assertTrue(true);

    }

    @Test
    void findId()
    {
        assertEquals("VW", testedRepo.findId(0).getMake());
        assertNull(testedRepo.findId(-42069));
        assertEquals(2000, testedRepo.findId(2).getYear());
    }

    @Test
    void findAll()
    {
        assertEquals(3, testedRepo.findAll().size());
        Seller newSeller = new Seller("Klaus", "AmUnCreierAMG", "Bucale");
        Advert newAd = new Car(newSeller, 3, "BMW", "X3", 2022, 1995, 204, 230, false, true, 5, 5, 45000, 30000 );
        testedRepo.add(newAd);
        assertEquals(4, testedRepo.findAll().size());
        testedRepo.delete(0);
        testedRepo.delete(1);
        assertEquals(2, testedRepo.findAll().size());
    }
}