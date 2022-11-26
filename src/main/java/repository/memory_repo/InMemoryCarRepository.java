package repository.memory_repo;

import model.Advert;
import model.Seller;
import repository.AdsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCarRepository implements AdsRepository {
    private List<Advert> advertList;
    private int currentID = 0;

    public InMemoryCarRepository() {
        this.advertList = new ArrayList<>();
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