package exceptions;

public class InvalidCredsException extends Exception
{
    public InvalidCredsException()
    {
        super("Invalid credentials provided!");
    }
}
