package repository.db_repo;

import exceptions.IllegalIdException;
import model.Benutzer;
import repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

public class DBUserRepository implements UserRepository
{
    private final EntityManager manager;

    /**
     * initializer for the database repository
     * @throws PersistenceException if the connection with the database fails
     */
    public DBUserRepository()
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
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
    public void delete(String username) throws IllegalIdException {
        manager.getTransaction().begin();
        Benutzer a = manager.find(Benutzer.class, username);
        if(a!=null)
            manager.remove(a);
        else
            throw new IllegalIdException();
        manager.getTransaction().commit();
    }

    @Override
    public void update(String username, Benutzer benutzer) throws IllegalIdException {
        manager.getTransaction().begin();
        Benutzer a = manager.find(Benutzer.class, username);
        if(a!=null)
        {
            a.update(benutzer);
            manager.merge(a);
        }
        else
            throw new IllegalIdException();
        manager.getTransaction().commit();
    }

    @Override
    public Benutzer findId(String username) throws IllegalIdException {
        manager.getTransaction().begin();
        Benutzer a = manager.find(Benutzer.class, username);
        manager.getTransaction().commit();
        if(a==null)
            throw new IllegalIdException();
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

        Benutzer foundBenutzer = null;
        try
        {
            foundBenutzer = findId(username);
        }
        catch (IllegalIdException e)
        {
            return null;
        }

        if(foundBenutzer.getPassword().equals(password))
            return foundBenutzer;
        return null;
    }
}
