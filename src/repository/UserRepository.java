package repository;

import model.User;

public interface UserRepository extends ICrudRepository<String, User>
{
    public User findByUserAnsPass(String username, String password);
}
