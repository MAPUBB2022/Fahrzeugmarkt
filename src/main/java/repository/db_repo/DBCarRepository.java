package repository.db_repo;

import exceptions.IllegalIdException;
import model.Advert;
import model.Seller;
import model.Transaktion;
import repository.AdsRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class DBCarRepository implements AdsRepository
{
    private EntityManagerFactory factory;
    private EntityManager manager;

    /**
     * initializer for the database repository
     * @throws PersistenceException if the connection with the database fails
     */
    public DBCarRepository()
    {
        factory = Persistence.createEntityManagerFactory("default");
        manager = factory.createEntityManager();
    }

    @Override
    public List<Advert> getAllAdsFromSeller(Seller s)
    {
        manager.getTransaction().begin();
        List<Advert> list  = manager.createQuery("SELECT a FROM Advert a where a.seller=:seller", Advert.class ).setParameter("seller", s).getResultList();
        manager.getTransaction().commit();

        return list;
    }

    @Override
    public List<Advert> getAllAdsFromToday()
    {
        LocalDate today = LocalDate.now(ZoneId.of("GMT+02:00"));
        manager.getTransaction().begin();
        List<Advert> list  = manager.createQuery("SELECT a FROM Advert a where a.placeDate=:date", Advert.class ).setParameter("date", today).getResultList();
        manager.getTransaction().commit();

        return list;
    }

    @Override
    public void add(Advert advert)
    {
        manager.getTransaction().begin();
        manager.persist(advert);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) throws IllegalIdException {
        manager.getTransaction().begin();
        Advert a = manager.find(Advert.class, id);
        if(a!=null)
            manager.remove(a);
        else
            throw new IllegalIdException();
        manager.getTransaction().commit();
    }

    @Override
    public void update(Integer id, Advert newAdvert) throws IllegalIdException {
        manager.getTransaction().begin();
        Advert a = manager.find(Advert.class, id);
        if(a!=null)
        {
            a.update(newAdvert);
            manager.merge(a);
        }
        else
            throw new IllegalIdException();

        manager.getTransaction().commit();
    }

    @Override
    public Advert findId(Integer id) throws IllegalIdException {
        manager.getTransaction().begin();
        Advert a = manager.find(Advert.class, id);
        manager.getTransaction().commit();
        if (a==null)
            throw new IllegalIdException();
        return a;
    }

    @Override
    public List<Advert> findAll()
    {
        manager.getTransaction().begin();
        List<Advert> list = manager.createQuery("SELECT a FROM Advert a", Advert.class ).getResultList();
        manager.getTransaction().commit();

        return list;
    }
}
