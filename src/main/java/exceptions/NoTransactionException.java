package exceptions;

public class NoTransactionException extends Exception
{
    public NoTransactionException()
    {
        super("There is no transaction available!");
    }
}
