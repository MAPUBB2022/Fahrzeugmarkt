package repository.db_repo;

import model.Admin;
import model.Benutzer;
import model.Buyer;
import model.Seller;
import repository.UserRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class DBUserRepository implements UserRepository
{
    private EntityManagerFactory factory;
    private EntityManager manager;

    /**
     * initializer for the database repository
     * @throws PersistenceException if the connection with the database fails
     */
    public DBUserRepository()
    {
        factory = Persistence.createEntityManagerFactory("default");
        manager = factory.createEntityManager();
    }

    @Override
    public void add(Benutzer benutzer)
    {
        manager.getTransaction().begin();
        manager.persist(benutzer);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(String username)
    {
        manager.getTransaction().begin();
        Benutzer a = manager.find(Benutzer.class, username);
        manager.remove(a);
        manager.getTransaction().commit();
    }

    @Override
    public void update(String username, Benutzer benutzer)
    {
        manager.getTransaction().begin();
        Benutzer a = manager.find(Benutzer.class, username);

        a.update(benutzer);

        manager.merge(a);
        manager.getTransaction().commit();
    }

    @Override
    public Benutzer findId(String username)
    {
        manager.getTransaction().begin();
        Benutzer a = manager.find(Benutzer.class, username);
        manager.getTransaction().commit();

        return a;
    }

    @Override
    public List<Benutzer> findAll()
    {
        manager.getTransaction().begin();
        List<Benutzer> list = manager.createQuery("SELECT a FROM Benutzer a", Benutzer.class ).getResultList();
        manager.getTransaction().commit();

        return list;
    }

    @Override
    public Benutzer findByUserAnsPass(String username, String password) {

        Benutzer foundBenutzer = findId(username);

        if(foundBenutzer.getPassword().equals(password))
            return foundBenutzer;
        return null;
    }
}
