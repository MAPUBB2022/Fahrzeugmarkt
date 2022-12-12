package exceptions;

public class IllegalIdException extends Exception {
    public IllegalIdException()
    {
        super("Invalid ID provided!");
    }
}
