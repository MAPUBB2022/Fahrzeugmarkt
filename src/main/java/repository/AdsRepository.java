package repository;

import model.Advert;
import model.Seller;

import java.util.ArrayList;
import java.util.List;

public interface AdsRepository extends ICrudRepository<Integer, Advert>
{
    /**
     * gets all ads from the repository that belong to a certain seller
     * @param s the seller that the ads should belong to
     * @return a list of all ads that belong to the given seller
     */
    List<Advert> getAllAdsFromSeller(Seller s);

    /**
     * gets all ads from the repository that have been listed today
     * @return a list of all adverts listed today
     */
    List<Advert> getAllAdsFromToday();
}
