package repository.db_repo;

import model.Advert;
import model.Transaktion;
import repository.TransactionRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

public class DBTransactionRepository implements TransactionRepository
{
    private EntityManagerFactory factory;
    private EntityManager manager;

    /**
     * initializer for the database repository
     * @throws PersistenceException if the connection with the database fails
     */
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

        a.update(transaktion);

        manager.merge(a);
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
