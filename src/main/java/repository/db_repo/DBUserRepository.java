package repository.db_repo;

import model.Advert;
import model.Benutzer;
import repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DBUserRepository implements UserRepository
{
    private EntityManagerFactory factory;
    private EntityManager manager;

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

        a.setLocation(benutzer.getLocation());
        a.setPassword(benutzer.getPassword());
        // TODO ce facem cu valorile specifice buyer si seller?

        manager.merge(a); // trebuie?
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
    public Benutzer findByUserAnsPass(String username, String password)
    {
        manager.getTransaction().begin();
        Query query = manager.createNativeQuery("select * from Benutzer u where username=:user_name", Benutzer.class);
        query.setParameter("user_name", username);
        Benutzer foundBenutzer = (Benutzer) query.getSingleResult();
        manager.getTransaction().commit();
        if(foundBenutzer.getPassword().equals(password))
            return foundBenutzer;
        return null;
    }
}
