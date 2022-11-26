package repository.db_repo;

import model.Advert;
import model.Seller;
import model.Transaktion;
import repository.TransactionRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DBTransactionRepository implements TransactionRepository
{
    private EntityManagerFactory factory;
    private EntityManager manager;

    public DBTransactionRepository()
    {
        factory = Persistence.createEntityManagerFactory("default");
        manager = factory.createEntityManager();
    }

    @Override
    public void add(Transaktion transaktion)
    {
        manager.getTransaction().begin();
        manager.persist(transaktion);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Integer id)
    {
        manager.getTransaction().begin();
        Transaktion a = manager.find(Transaktion.class, id);
        manager.remove(a);
        manager.getTransaction().commit();
    }

    @Override
    public void update(Integer id, Transaktion transaktion)
    {
        manager.getTransaction().begin();
        Transaktion a = manager.find(Transaktion.class, id);

        a.setAd(transaktion.getAd());
        a.setAmount(transaktion.getAmount());
        a.setBid(transaktion.isBid());
        a.setBuyer(transaktion.getBuyer());

        manager.merge(a); // trebuie?
        manager.getTransaction().commit();
    }

    @Override
    public Transaktion findId(Integer id)
    {
        manager.getTransaction().begin();
        Transaktion a = manager.find(Transaktion.class, id);
        manager.getTransaction().commit();

        return a;
    }

    @Override
    public List<Transaktion> findAll()
    {
        manager.getTransaction().begin();
        List<Transaktion> list = manager.createQuery("SELECT a FROM Transaktion a", Transaktion.class ).getResultList();
        manager.getTransaction().commit();

        return list;
    }

    @Override
    public List<Transaktion> getTransactionsByCar(Advert advert)
    {
        manager.getTransaction().begin();
        List<Transaktion> list = manager.createQuery("SELECT a FROM Transaktion a where a.ad=:advert", Transaktion.class ).setParameter("advert", advert).getResultList();
        manager.getTransaction().commit();

        return list;
    }
}
