package repository.memory_repo;

import model.Admin;
import model.Benutzer;
import model.Buyer;
import model.Seller;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository
{
    private List<Benutzer> allBenutzers;

    public InMemoryUserRepository()
    {
        this.allBenutzers = new ArrayList<>();
        //populate();
    }

    private void populate()
    {
        this.add(new Buyer("andreigali","42069","Cristian, BV"));
        this.add(new Buyer("iordache","melissa","Brasov, BV"));
        this.add(new Seller("veriku","iazivericule","Pitesti, AG"));
        this.add(new Admin("vincenzo","gen","pe Italia"));
    }

    @Override
    public void add(Benutzer benutzer)
    {
        //TODO de verificat daca username ul e disponibil
        boolean validUser = true;
        for(Benutzer u: allBenutzers)
        {
            if(u.getUsername().equals(benutzer.getUsername()))
            {
                validUser = false;
                break;
            }
        }
        if(validUser)
            allBenutzers.add(benutzer);
    }

    @Override
    public void delete(String username)
    {
        Benutzer u = findId(username);
        if(u!=null)
            allBenutzers.remove(u);
    }

    @Override
    public void update(String username, Benutzer newBenutzer)
    {
        Benutzer u = findId(username);
        if(u!=null)
        {
            newBenutzer.setUsername(username);
            allBenutzers.set(allBenutzers.indexOf(u), newBenutzer);
        }
    }

    @Override
    public Benutzer findId(String username)
    {
        for(Benutzer u: allBenutzers)
        {
            if(u.getUsername().equals(username))
            {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Benutzer> findAll() {
        return allBenutzers;
    }


    @Override
    public Benutzer findByUserAnsPass(String username, String password)
    {
        for(Benutzer u: allBenutzers)
        {
            if(u.getUsername().equals(username) && u.getPassword().equals(password))
            {
                return u;
            }
        }
        return null;
    }
}
