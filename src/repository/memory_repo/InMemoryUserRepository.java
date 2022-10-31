package repository.memory_repo;

import model.Admin;
import model.Buyer;
import model.Seller;
import model.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository
{
    private List<User> allUsers;

    public InMemoryUserRepository() {
        this.allUsers = new ArrayList<>();
    }

    public void populateUsers()
    {
        allUsers.add(new Buyer("andreigali","42069","Cristian, BV"));
        allUsers.add(new Buyer("iordache","melissa","Brasov, BV"));
        allUsers.add(new Seller("veriku","iazivericule","Pitesti, AG"));
        allUsers.add(new Admin("vincenzo","gen","pe Italia"));
    }

    @Override
    public void add(User user)
    {
        //TODO de verificat daca username ul e disponibil
        boolean validUser = true;
        for(User u: allUsers)
        {
            if(u.getUsername().equals(user.getUsername()))
            {
                validUser = false;
                break;
            }
        }
        if(validUser)
            allUsers.add(user);
    }

    @Override
    public void delete(String username)
    {
        User u = findId(username);
        if(u!=null)
            allUsers.remove(u);
    }

    @Override
    public void update(String username, User newUser)
    {
        User u = findId(username);
        if(u!=null)
        {
            allUsers.set(allUsers.indexOf(u), newUser);
        }
    }

    @Override
    public User findId(String username)
    {
        for(User u: allUsers)
        {
            if(u.getUsername().equals(username))
            {
                return u;
            }
        }
        return null;
    }


    @Override
    public User findByUserAnsPass(String username, String password)
    {
        for(User u: allUsers)
        {
            if(u.getUsername().equals(username) && u.getPassword().equals(password))
            {
                return u;
            }
        }
        return null;
    }
}
