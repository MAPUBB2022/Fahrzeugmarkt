package repository;

import model.Benutzer;

public interface UserRepository extends ICrudRepository<String, Benutzer>
{
    public Benutzer findByUserAnsPass(String username, String password);
}
