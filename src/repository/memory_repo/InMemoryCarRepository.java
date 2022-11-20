package repository.memory_repo;

import model.Advert;
import model.Car;
import model.Seller;
import model.User;
import repository.AdsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCarRepository implements AdsRepository {
    private List<Advert> advertList;
    private int currentID = 0;
    private void populate()
    {
        Advert a = new Car(20, "VW", "Taigo", 2022, 1499, 150, 200, false, false, 5, 4,23000, 6000);
        Advert b = new Car(20, "VW", "Passat", 2012, 1999, 150, 200, false, false, 5, 4, 11000, 3000);
        Advert c = new Car(20, "Dacia", "Papuc", 2000, 1299, 150, 200, false, false, 5, 4, 4000, 800);
        this.add(a);
        this.add(b);
        this.add(c);
    }
    public InMemoryCarRepository() {
        this.advertList = new ArrayList<>();
        populate();
    }
    @Override
    public List<Advert> getAllAdsFromSeller(Seller s)
    {
        List<Advert> result = new ArrayList<>();
        for (Advert ad : advertList)
        {
            if (ad.getSeller().getUsername().equals(s.getUsername()))
                result.add(ad);
        }
        return result;
    }

    @Override
    public List<Advert> getAllAdsFromToday() {
        List<Advert> result = new ArrayList<>();
        for (Advert ad : advertList)
        {
            if (ad.getPlaceDate().compareTo(LocalDate.now()) == 0)
                result.add(ad);
        }
        return result;
    }

    @Override
    public void add(Advert advert) {
        advert.setID(currentID++);
        advertList.add(advert);
    }

    @Override
    public void delete(Integer integer) {

        if (findId(integer) != null)
            advertList.remove((findId(integer)));
    }

    @Override
    public void update(Integer id, Advert advert) {

        if (findId(id) != null)
        {
            advert.setID(id);
            advertList.set(id, advert);
        }
        else
            System.out.println("Advert does not exists");

    }

    @Override
    public Advert findId(Integer integer) {
        for (Advert advert : advertList) {
            if (advert.getID() == integer)
                return advert;
        }
        return null;
    }

    @Override
    public List<Advert> findAll() {
        return advertList;
    }
}