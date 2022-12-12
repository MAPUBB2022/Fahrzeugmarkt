package repository;

import model.Benutzer;

public interface UserRepository extends ICrudRepository<String, Benutzer>
{
    /**
     * returns the user with the given set of username and password
     * @param username the username that is searched
     * @param password the password that is searched
     * @return the user with the given usernamme and password. if it doesn't exist, null is returned
     */
    public Benutzer findByUserAnsPass(String username, String password);
}
