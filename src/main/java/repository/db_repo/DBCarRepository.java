package repository.db_repo;

import model.Advert;
import model.Seller;
import repository.AdsRepository;

import javax.persistence.Entity;
import java.util.List;

public class DBCarRepository implements AdsRepository
{

    @Override
    public List<Advert> getAllAdsFromSeller(Seller s) {
        return null;
    }

    @Override
    public List<Advert> getAllAdsFromToday() {
        return null;
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

    @Override
    public List<Advert> findAll() {
        return null;
    }
}
