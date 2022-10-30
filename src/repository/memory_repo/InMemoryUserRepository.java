package repository.memory_repo;

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

    }

    @Override
    public void add(User user)
    {
        //TODO de verificat daca username ul e disponibil
        allUsers.add(user);
    }

    @Override
    public void delete(String username)
    {
        allUsers.remove(findId(username));
    }

    @Override
    public void update(String s, User user)
    {

    }

    @Override
    public User findId(String s)
    {
        return null;
    }


    @Override
    public User findByUserAnsPass(String username, String password) {
        return null;
    }
}
