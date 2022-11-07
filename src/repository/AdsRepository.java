package repository;

import model.Advert;
import model.Seller;

import java.util.ArrayList;
import java.util.List;

public interface AdsRepository extends ICrudRepository<Integer, Advert>
{
    List<Advert> getAllAdsFromSeller(Seller s);
    List<Advert> getAllAdsFromToday();
}
