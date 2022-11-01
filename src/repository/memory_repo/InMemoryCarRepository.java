package repository.memory_repo;

import model.Advert;
import model.User;
import repository.AdsRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCarRepository implements AdsRepository {
    private List<Advert> advertList;
    private int currentID = 0;
    public InMemoryCarRepository() {
        this.advertList = new ArrayList<>();
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
    public void update(Integer integer, Advert advert) {

        if (findId(integer) != null)
        {
            advertList.set(integer, advert);
            // sa pastreze id ul
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
}