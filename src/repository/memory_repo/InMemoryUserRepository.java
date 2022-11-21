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

    public InMemoryUserRepository()
    {
        this.allUsers = new ArrayList<>();
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
            newUser.setUsername(username);
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
    public List<User> findAll() {
        return allUsers;
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
