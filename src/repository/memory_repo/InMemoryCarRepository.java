package repository.memory_repo;

import model.Advert;
import model.User;
import repository.AdsRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCarRepository implements AdsRepository
{
    private List<Advert> advertList;

    public InMemoryCarRepository() {
        this.advertList = new ArrayList<>();
    }

    @Override
    public void add(Advert advert) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Advert advert) {

    }

    @Override
    public Advert findId(Integer integer) {
        return null;
    }
}
